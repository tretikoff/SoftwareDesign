# Kotlin CLI

Реализация командной строки bash на kotlin

### Поддерживаемая функциональность

- [ ] Одинарные и двойные кавычки (full and weak quoting)
- [x] Переменные
- [x] Оператор $
- [ ] Вызов внешних процессов
- [x] Пайплайны (оператор |)
- [x] readme.md
- [x] добавить тесты
- [x] использовать линтер
- [x] вынести линтеры, тесты в gradle
- [ ] вынести gradle в CI

### Реализованные команды

- [x] cat [FILE] - выводит содержимое файла на экран
- [x] echo - выводит на экран свои аргументы
- [x] wc [FILE] - выводит количество строк, слов и байт в файле
- [x] pwd - выводит текущую директорию
- [x] exit - выходит из интерпретатора

### Скрипты
- [x] `gradle build` для компиляции проекта
- [x] `gradle run` для запуска коммандной строки
- [ ] `gradle extern -Pfile="filename"` для запуска внешнего процесса из файла
- [x] `gradle test` для запуска тестов
- [x] `gradle lint` для запуска линтера
- [x] `gradle format` для запуска форматирования кода

Используемый линтер - [ktlint](https://github.com/pinterest/ktlint).

В качестве шаблона проектирования используется паттерн "Абстрактная фабрика", она позволяет создавать комманды.
Для создания новой команды нужно унаследоваться от класса `Command` и добавить создание комманды в `CommandFactory`