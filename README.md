# NNPIA - backend
Repozitář backendové aplikace semestrálního projektu do NNPIA obsahující zdrojové kódy psané v technologie Java Spring Boot.

| Master |
|--------|
| [![Java CI with Maven][CI badge master]][CI actions master] |

### O projektu
Viz [frontend][Frontend o projektu].

## Popis rozhraní
Celá aplikace implementuje kompletní REST rozhraní pro práci s daty na [frontendové][Frontend] části, psané pomocí technologie React. 
#### Autentizace
Veškeré REST API je zabezpečeno autentizačním procesem vyžadujícím společně s dotazem zaslat i autentizační Bearer token. Tento token je platný po vymezený časový interval a je ho možné získat po registraci nebo po přihlášení do aplikace. 
#### Struktura dotazu
Pro získání Bearer tokenu je potřeba na API server odeslat požadavek s autentizačními udaji. Tyto údaje musí být serveru předány metodou POST a musí obsahovat uživatelské jméno a heslo.
Požadavek je nutné zaslat metodou `POST` na adresu `https://nnpiabackend.herokuapp.com/security/login`. 
##### Obsah požadavku:
```json
{
  "username": "<uživatelské jméno>",
  "password": "<heslo>"
}
```
Veškeré další požadavky jsou obdobné, některé nevyžadují vyplněné tělo dotazu.
#### Struktura odpovědi
Každá odpověď od serveru je zabalena do struktury popisující, v jakém stavu byl požadavek na serveru vyřízen. 
* `status` - HTTP stavový kód pod kterým server odpověď vyřídil
* `status_key` - Textový řetězec identifikující odpověď druh vrácených dat 
* `result` - Strukturovaná data vrácena serverem (mohou být NULL)
* Příklad:
    * `ALREADY-EXISTS` - položka s daty dle definice serveru již existují (neplatí pro autentizaci)
    * `NOT-FOUND` - hledaná položka nebyla nalezena (neplatí pro autentizaci)
```json
{
  "status": 200,
  "status_key": "SUCCESS",
  "result": {
    "username": "user",
    "token": "qyJGbvciOoJIOzI5NiJ4.eyJzdWIiOiJqYW5da2RvbSIsImV4cC... <zkráceno>"
  }
}
```
Zde v příkladu je vidět odpověď serveru, který vrací autentizační token a uživatelské jméno přihlášeného uživatele.
#### Odpověď se stránkováním
Pro datové výpisy server vrací odpověď ve struktuře pro snadné stránkování.
```json
{
  "status": 200,
  "status_key": "SUCCESS",
  "result": {
    "content": [
      {
        "id": 16,
        "firstname": "Albert ",
        "surname": "Einstein",
        "country": "USA"
      },
      {
        "id": 17,
        "firstname": "Oscar ",
        "surname": "Wilde",
        "country": "UK"
      },
      {
        "id": 18,
        "firstname": "Ralph ",
        "surname": "Waldo Emerson",
        "country": "USA"
      },
      {
        "id": 19,
        "firstname": "Frank ",
        "surname": "Tyger",
        "country": "USA"
      },
      {
        "id": 20,
        "firstname": "Henry ",
        "surname": "Ford",
        "country": "USA"
      }
    ],
    "pageable": {
      "sort": {
        "sorted": false,
        "unsorted": true,
        "empty": true
      },
      "pageNumber": 0,
      "pageSize": 5,
      "offset": 0,
      "unpaged": false,
      "paged": true
    },
    "last": false,
    "totalElements": 14,
    "totalPages": 3,
    "first": true,
    "sort": {
      "sorted": false,
      "unsorted": true,
      "empty": true
    },
    "numberOfElements": 5,
    "size": 5,
    "number": 0,
    "empty": false
  }
}
```

### API dokumentace
Celé REST API, komunikační rozhraní, je přehledně [zdokumentováno][Online API docs].

### Struktura databáze
Databází pro ukládání dat je MySQL s použitým enginem MyISAM. Jedná se o relační databázi.

![Model databáze][db_model]

<hr>
<strong>2020 - Bc. Dominik Janák</strong><br />
NNPIA - Univerzita Pardubice

[CI actions master]: https://github.com/janakdom/NNPIA_backend/actions
[CI badge master]: https://github.com/janakdom/NNPIA_backend/workflows/Java%20CI%20with%20Maven/badge.svg
[Online API docs]: https://nnpiabackend.herokuapp.com/swagger-ui.html#/quote-score-controller
[Frontend o projektu]: https://github.com/janakdom/NNPIA_frontend#o-projektu
[db_model]: ./db_model.png
