## Преобразование DTO в сущность

В этом домашнем задании мы будем работать над приложением сохранения 
контактов. Для упрощения будем сохранять только имя, фамилию и номер телефона. 
Вам необходимо добавить метод сохранения нового контакта.

### src/main/java/exercise/controller/ContactsController.java

Создайте в контроллере обработчик, который будет обрабатывать POST-запросы на 
адрес */contacts*. Обработчик должен принимать объект класса `ContactCreateDTO` и 
возвращать объект класса `ContactDTO`.
