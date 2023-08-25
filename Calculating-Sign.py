import hashlib

# Replace these with your actual values
salt = "1692449911453"
password = "ali ali ali"
usr = "nazir abida"
company_key = "bnrl_frRFjEz8Mkn"

# Calculate the SHA-1 hash of salt
salt_hash = hashlib.sha1(salt.encode()).hexdigest()

# Calculate the SHA-1 hash of password
password_hash = hashlib.sha1(password.encode()).hexdigest()

# Concatenate the strings and calculate the SHA-1 hash
concatenated_string = f"{salt_hash}&action=auth&usr={usr}&company-key={company_key}"
sign = hashlib.sha1(concatenated_string.encode()).hexdigest()

print(f"Calculated sign parameter: {sign}")
