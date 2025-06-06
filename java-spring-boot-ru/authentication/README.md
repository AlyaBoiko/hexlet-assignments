## Аутентификация

В этом упражнении мы сделаем простой блог. Пользователь может зарегистрироваться на нашем сервисе, создавать и редактировать статьи. Чтобы различать пользователей в системе, подключим аутентификацию. В основном все необходимое для CRUD операций в приложении уже создано. Вам предстоит подключить аутентификацию пользователей и реализовать создание новой статьи

### Полезные ссылки

* [Пример приложения на Spring Boot](https://github.com/hexlet-components/java-spring-blog/tree/main)

### build.gradle.kts

Добавьте в проект зависимости, необходимые для работы аутентификации по JWT-токену.

### src/main/java/exercise

В упражнении уже создана вся необходимая файловая структура. Вам предстоит подключить аутентификацию пользователя. Для входа пользователя в систему будем использовать его электронную почту. Внесите все необходимые изменения в файлы проекта, чтобы заработала аутентификация.

Настройте Spring Security так, чтобы без аутентификации пользователю были доступны только регистрация (создание нового пользователя) и логин. Все остальные действия должны быть не доступны пользователю, если он не аутентифицирован. При подключении аутентификации ориентируйтесь на материал урока.

### src/main/java/exercise/controller/ArticleController.java

Реализуйте маршрут для создания новой статьи. Обработчик принимает POST запросы на адрес */articles*. Автором статьи становится текущий пользователь.

Для получения текущего пользователя можете реализовать соответствующий метод в классе `UserUtils`.

### Подсказки

* Если при решении возникнут сложности, заглядывайте в наш [эталонный репозиторий](https://github.com/hexlet-components/java-spring-blog/tree/main) и материал урока
* При старте приложения в системе уже есть пользователь-администратор. Для экспериментов можете использовать его данные или зарегистрировать собственного пользователя. Загляните в файл *src/main/java/component/DataInitializer*
* Метод [requestMatchers()](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/AbstractRequestMatcherRegistry.html#requestMatchers(org.springframework.http.HttpMethod,java.lang.String...))
