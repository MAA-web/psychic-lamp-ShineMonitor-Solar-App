#include <iostream>
#include <string>
#include <sstream>
#include <iomanip>
#include <openssl/sha.h>
#include <curl/curl.h>

// Function to calculate the SHA-1 hash of a string
std::string calculateSHA1(const std::string& input) {
    unsigned char hash[SHA_DIGEST_LENGTH];
    SHA1(reinterpret_cast<const unsigned char*>(input.c_str()), input.length(), hash);

    std::stringstream ss;
    for (int i = 0; i < SHA_DIGEST_LENGTH; ++i) {
        ss << std::hex << std::setw(2) << std::setfill('0') << static_cast<int>(hash[i]);
    }

    return ss.str();
}

// Callback function to write response data
size_t WriteCallback(void* contents, size_t size, size_t nmemb, std::string* output) {
    size_t total_size = size * nmemb;
    output->append(reinterpret_cast<char*>(contents), total_size);
    return total_size;
}

int main() {
    // Replace these with your actual values
    std::string salt = "1692473539741";
    std::string password = "your_password";
    std::string usr = "your_username";
    std::string company_key = "your_company_key";

    // Calculate the SHA-1 hash of salt
    std::string salt_hash = calculateSHA1(salt);

    // Calculate the SHA-1 hash of password
    std::string password_hash = calculateSHA1(password);

    // Concatenate the strings
    std::string concatenated_string = salt_hash + "&action=auth&usr=" + usr + "&company-key=" + company_key;

    // Calculate the SHA-1 hash of the concatenated string
    std::string sign = calculateSHA1(concatenated_string);

    std::cout << "Calculated sign parameter: " << sign << std::endl;

    // URL to send the request to
    std::string url = "http://android.shinemonitor.com/public/?sign=" + sign + "&salt=" + salt;

    CURL* curl;
    CURLcode res;

    curl_global_init(CURL_GLOBAL_DEFAULT);
    curl = curl_easy_init();

    if (curl) {
        // Set the URL
        curl_easy_setopt(curl, CURLOPT_URL, url.c_str());

        // Perform the HTTP GET request
        res = curl_easy_perform(curl);

        if (res == CURLE_OK) {
            std::cout << "Request successful." << std::endl;
        } else {
            std::cerr << "Request failed: " << curl_easy_strerror(res) << std::endl;
        }

        // Clean up and release resources
        curl_easy_cleanup(curl);
    } else {
        std::cerr << "Error initializing libcurl." << std::endl;
    }

    curl_global_cleanup();

    return 0;
}
