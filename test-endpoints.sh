#!/bin/bash

BASE_URL="http://localhost:8080/api/v1"
EMAIL="admin@example.com"
PASSWORD="admin123"

echo "=== 1. Register User ==="
curl -X POST "$BASE_URL/auth/register" \
  -H "Content-Type: application/json" \
  -d "{
    \"name\": \"Test User\",
    \"email\": \"$EMAIL\",
    \"password\": \"$PASSWORD\"
  }"
echo -e "\n"

# Note: In a real scenario, you'd need to activate the user via the token printed in the console logs
# or database before logging in.
echo "NOTE: Silakan cek log aplikasi atau database untuk mendapatkan token aktivasi user '$EMAIL' jika diperlukan."
echo "      Akses endpoint: GET /api/v1/auth/activate?email=$EMAIL&token=..."
echo -e "\n"

echo "=== 2. Login User ==="
LOGIN_RESPONSE=$(curl -s -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d "{
    \"email\": \"$EMAIL\",
    \"password\": \"$PASSWORD\"
  }")

echo "Response: $LOGIN_RESPONSE"

# Extract Token (Simple extraction, assumes JSON response like {"token": "..."})
# If you have 'jq' installed, it's better: TOKEN=$(echo $LOGIN_RESPONSE | jq -r .token)
TOKEN=$(echo $LOGIN_RESPONSE | grep -o '"token":"[^"]*' | grep -o '[^"]*$')

if [ -z "$TOKEN" ]; then
  echo "Gagal login atau token tidak ditemukan. Pastikan user sudah aktif."
  exit 1
fi

echo "Token: $TOKEN"
echo -e "\n"

echo "=== 3. Get User Profile ==="
curl -X GET "$BASE_URL/users/me" \
  -H "Authorization: Bearer $TOKEN"
echo -e "\n"

echo "=== 4. Create Product ==="
curl -X POST "$BASE_URL/products" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Produk Test",
    "description": "Deskripsi produk test",
    "price": 10000
  }'
echo -e "\n"

echo "=== 5. List Products ==="
curl -X GET "$BASE_URL/products"
echo -e "\n"
