# Дипломный проект профессии «Тестировщик»
#Тестовая документация:
[План тестирования](Plan.md)
[Отчет по итогам тестирования](Report.md)
[Итоги автоматизации](Summary.md)
## Инструкция по запуску

1. Склонировать репозиторий <code>git clone https://github.com/ModestVale/diplom.git</code>
2. Перейти в папку <code>diplom</code> 
3. Запустить контейнеры docker:  <code>docker-compose up</code>
4. Запустить тесты:  <code>gradlew allureReport --depends-on-tests</code>
5. Запустить Allure для просмотра отчета: <code>gradlew allureServe</code>
