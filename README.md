# blog-project

ROLE_ADMIN given to user in database


Endpoints:
User:

POST localhost:8080/api/auth/login

POST localhost:8080/api/auth/signup

GET  localhost:8080/api/auth/users/role

GET  localhost:8080/api/auth/users/all

PUT  localhost:8080/api/auth/users/updateUser/{id}

POST localhost:8080/api/auth/users/changePassword/{id}

DELETE localhost:8080/api/auth/users/delete/{id}

GET localhost:8080/api/auth/users/{id}

Posts:

DELETE localhost:8080/api/auth/users/delete

POST localhost:8080/api/auth/posts/newPost

DELETE localhost:8080/api/auth/posts/delete/{postId}

POST localhost:8080/api/auth/posts/newPost

GET localhost:8080/api/auth/users/{id}

DELETE localhost:8081/api/users/{id}

POST localhost:8080/api/auth/posts/newPost
