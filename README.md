## IMovidaConfig
- viene fatto all'inizio del carimento dei dati, poiché tale comando cancella eventuali vecchi dati.

## IMovidaDB

### loadFromFile:
- il file che carica deve essere nella directory principale, altrimenti lancia un errore.
- se un dato è errato, carica tutti i film precedenti e lancia un errore.

### saveToFile
- salva in base ad un certo ordine:
    - ABR \- segue un ordine "in-visita";
    - ListaCollegataNonOrdinata \- segue l'ordine di inserimento;

### clear
- cancella tutti i dati nel database.
- seleziona come dizionario ABR e come algoritmo di ordinamento BubbleSort.

### countPeople
- conta sia gli attori che i registi.

### getMovieByTitle
- l'input deve essere il titolo esatto contenente nel database
- se l'input non esiste, ritorna null

### getPersonByName
- l'input deve essere il nome (e cognome) esatto contenente nel database
- se l'input non esiste, ritorna null

### getAllMovies
- salva in base ad un certo ordine:
    - ABR \- segue un ordine "in-visita";
    - ListaNonOrdinata \- segue l'ordine di inserimento;
### getAllPeople
- salva in base ad un certo ordine:
    - ABR \- segue un ordine "in-visita";
    - ListaNonOrdinata \- segue l'ordine di inserimento;

## IMovidaSearch
- l'input se è una stringa è key sensitive.
- l'input deve avere lo stesso nome dell'oggetto che si ricerca, altrimenti non lo si trova.

## searchMovieByTitle
- solo in questo caso si cerca tutti i film con una certa stringa.
