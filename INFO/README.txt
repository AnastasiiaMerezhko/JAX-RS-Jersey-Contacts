Отримання всіх контактів
GET
http://localhost:8082/api/v1.0/contacts

Отримання контакту за id
GET
http://localhost:8082/api/v1.0/contacts/4

Створення нового контакту
POST
http://localhost:8082/api/v1.0/contacts

Налаштування в Postman: Body, raw, JSON.

{
    "id": 7,
    "name": "John",
    "phone": "444 333-7677"
}

Оновлення контакту за id
PUT
http://localhost:8082/api/v1.0/contacts/2

Налаштування в Postman: Body, raw, JSON.

{
    "phone": "111 878-9999"
}


Видалення контакту за id
DELETE
http://localhost:8082/api/v1.0/contacts/1
