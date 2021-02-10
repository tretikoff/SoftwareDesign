# Kotlin CLI

Реализация командной строки bash на kotlin

### Поддерживаемая функциональность

- [ ] Одинарные и двойные кавычки (full and weak quoting)
- [x] Переменные
- [x] Оператор $
- [ ] Вызов внешних процессов
- [x] Пайплайны (оператор |)
- [x] readme.md
- [ ] добавить тесты
- [ ] использовать линтер
- [ ] вынести линтеры, тесты в gradle
- [ ] вынести gradle в CI

### Реализованные команды

- [x] cat [FILE] - выводит содержимое файла на экран
- [x] echo - выводит на экран свои аргументы
- [x] wc [FILE] - выводит количество строк, слов и байт в файле
- [x] pwd - выводит текущую директорию
- [x] exit - выходит из интерпретатора