import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//import okio.Buffer;
//import okio.Segment;
//import okio.Timeout;
//import okio.ByteString;

public class MainAppDecompiledHashing {

    public static class Source<V> implements Observer<V> {
        final LiveData<V> mLiveData;
        final Observer<? super V> mObserver;
        int mVersion = -1;

        Source(LiveData<V> liveData, Observer<? super V> observer) {
            this.mLiveData = liveData;
            this.mObserver = observer;
        }

        /* access modifiers changed from: package-private */
        public void plug() {
            this.mLiveData.observeForever(this);
        }

        /* access modifiers changed from: package-private */
        public void unplug() {
            this.mLiveData.removeObserver(this);
        }

        public void onChanged(@Nullable V v) {
            if (this.mVersion != this.mLiveData.getVersion()) {
                this.mVersion = this.mLiveData.getVersion();
                this.mObserver.onChanged(v);
            }
        }
    }

    public abstract class ForwardingSource implements Source {
        private final Source delegate;

        public ForwardingSource(Source source) {
            if (source != null) {
                this.delegate = source;
            } else {
                throw new IllegalArgumentException("delegate == null");
            }
        }

        public final Source delegate() {
            return this.delegate;
        }

        public long read(Buffer buffer, long j) throws IOException {
            return this.delegate.read(buffer, j);
        }

        public Timeout timeout() {
            return this.delegate.timeout();
        }

        public void close() throws IOException {
            this.delegate.close();
        }

        public String toString() {
            return getClass().getSimpleName() + "(" + this.delegate.toString() + ")";
        }
    }

    public static class HashingSource extends ForwardingSource {
        private final MessageDigest messageDigest;

        public HashingSource(Source source, String str) {
            super(source);
            try {
                this.messageDigest = MessageDigest.getInstance(str);
            } catch (NoSuchAlgorithmException unused) {
                throw new AssertionError();
            }
        }

        /*public long read(Buffer buffer, long j) throws IOException {
            long read = super.read(buffer, j);
            if (read != -1) {
                long j2 = buffer.size - read;
                long j3 = buffer.size;
                Segment segment = buffer.head;
                while (j3 > buffer.size - read) {
                    segment = segment.prev;
                    j3 -= (long) (segment.limit - segment.pos);
                }
                while (j3 < buffer.size) {
                    int i = (int) ((((long) segment.pos) + j2) - j3);
                    this.messageDigest.update(segment.data, i, segment.limit - i);
                    j2 = ((long) (segment.limit - segment.pos)) + j3;
                    j3 = j2;
                }
            }
            return read;
        }*/

        public ByteString hash() {
            return ByteString.of(this.messageDigest.digest());
        }
    }

    public static class Misc {

        public static HashingSource sha1(String source) {
            return new HashingSource(source, "SHA-1");
        }

        public static final String byte2HexStr(byte[] bArr) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(byteArrayOutputStream);
            for (int i = 0; i < bArr.length; i++) {
                printStream.printf("%02X", new Object[]{Byte.valueOf(bArr[i])});
            }
            return byteArrayOutputStream.toString();
        }

        public static final String sha1StrLowerCase(byte[] bArr) {
            return byte2HexStr(sha1(bArr).hash().toByteArray()).toLowerCase();
        }
    }

    public static void main(String[] args) {
        System.out.println(Misc.sha1StrLowerCase("HI".getBytes()));
    }
}
