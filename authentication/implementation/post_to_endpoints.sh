# Sucessful Account Creation
curl -k -v --cert ./clientkeystore.pem -X POST 'https://localhost:8084/authentication/create?accountNumber=77853449&userName=david&password=daviddied'

# Failure account creation | Because of wrong password
curl -k -v --cert ./clientkeystore.pem -X POST 'https://localhost:8084/authentication/create?accountNumber=77853449&userName=david&password=david'


# Failure account creation | Becasue of wrong account number
curl -k -v --cert ./clientkeystore.pem -X POST 'https://localhost:8084/authentication/create?accountNumber=7785344&userName=david&password=david'


# Authentication success
curl -k -v --cert ./clientkeystore.pem -X POST 'https://localhost:8084/authentication/authenticate?userName=david&password=daviddied'


# Authentication Failure
curl -k -v --cert ./clientkeystore.pem -X POST 'https://localhost:8084/authentication/create?accountNumber=77853449&userName=david&password=daviddie'
