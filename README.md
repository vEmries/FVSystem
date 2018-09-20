# FVSystem

Aplikacja Java Spring działająca jako RESTful API oraz front stworzony z wykorzystanie AngularJS (1.5).

Aplikacja służąca do monitorowania i zarządzania fakturami przychodzącymi oraz płatnościami. Waliduje dane podczas tworzenia i edycji
faktur, płatności czy kontrahentów oraz automatyzuje część związanych z tym procesów (m. in. obliczanie stanu płatności i pozostałej kwoty,
archiwizowanie tylko faktur opłaconych i po terminie płatności, podświetlanie faktur nieopłaconych ze zbliżającym się terminem płatności).
Ponadto aplikacja zapewnia autoryzację użytkownika i umożliwia zarządzanie dostępem do konkretnych mapowań dla grup użytkowników.

API odpowiada za połączenie z bazą danych, zarządzanie encjami oraz obsługę zapytań $http otrzymanych od webowego UI.
UI umożliwia łatwy dostęp do wszystkich funkcjonalności systemu.

# Demo

Działająca aplikacja uruchomiona na Heroku: [fv-system-demo.herokuapp.com](https://fv-system-demo.herokuapp.com/)  
Dane do logowania: user_demo / pw_demo

# Moduły wykorzystane do stworzenia front-endu

[alexcrack Angular-UI-Notification](https://github.com/alexcrack/angular-ui-notification)  
[720kb AngularJS Datepicker](https://github.com/720kb/angular-datepicker)  
[Creative Tim CSS Style](https://github.com/creativetimofficial/material-dashboard)  