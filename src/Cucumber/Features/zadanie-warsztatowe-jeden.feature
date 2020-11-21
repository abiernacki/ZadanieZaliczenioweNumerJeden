Feature: Zmiana informacji uzytkownika
  Scenario: uzytkownik moze dodac nowy adres

    Given wlaczamy strone sklepu PrestaShop, mamy zarejestrowanego uzytkownika
    When logowanie na stworzonego uzytkownika i wcisniecie kafelka adresses
    And klikniecie w Create new address i wypełnienie formularza
    Then sprawdzenie czy dane w podanym adresie sa poprawne
