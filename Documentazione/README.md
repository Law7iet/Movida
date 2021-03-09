# Movida
Nome del gruppo: HanChu
Componenti: Han Chu - 0000882776

## Istruzioni per il Test
Per eseguire il programma test bisogna avere installato il [Java Development Kit](https://www.oracle.com/java/technologies/javase-downloads.html).
1. Scaricare la repository
2. Aprire il terminale e posizionarsi nella cartella `Movida/`
3. Digitare `make` per compilare i file
4. Digitare `make run` per eseguire il test
5. Per cancellare i *Byte Code* digitare `make clean`.

## Implementazione
Un'istanza di MovidaCore deve contenere i metodi di IMovidaConfig, IMovidaDB, IMovidaSearch e IMovidaCollaboration.
I campi di MovidaCore sono:
- 3 dizionari per i film, gli attori e i registi;
- un algoritmo di ordinamento;
- un grafo per le collaborazioni.
I dizionari sono di due tipi:
- ListaNonOrdinata
- ABR
L'algoritmo di ordinamento sono di due tipi:
- BubbleSort
- MergeSort

### Dizionario
È un contenitore di dati, che possono essere 'Movie' o 'Person'. Avendo i stessi metodi, li ho raggruppati nell'interfaccia 'Dizionario'.

### Ordinamento
È un algoritmo per ordinare i dati. Dato che i dati si possono ordinare in vari modi, ho creato una classe astratta contenente dei campi che indicano l'ordine (si può odinare per i film in base al titolo, voto e anno, mentre per le persone per il nome e per il numero di film "fatti"), sia in senso crescente che decrescente.
È astratta poiché l'algoritmo viene implementato in BubbleSort.java e MergeSort.java.

### Grafo
Il grafo è un 'HashMap' con chiave una stringa (il nome dell'attore) e una lista di collaborazioni con 'actorA' l'attore il quale nome è la chiave.
Le collaborazioni si creano o si rimuovono se si aggiunge o rimuove un film. dunque il grafo ha due metodi per aggiungere e rimuovere tutte le collaborazioni generate dal film passato per parametro.
Inoltre le funzioni di IMovidaCollaboration sono implementate nel grafo poiché tale funzioni se definite al di fuori della classe, non potevano acceddere a dei campi.

### IMovidaConfig
- viene fatto all'inizio del carimento dei dati, poiché tale comando cancella eventuali vecchi dati.

### IMovidaDB

#### loadFromFile:
- il file che carica deve essere nella directory principale, altrimenti lancia un errore.
- se un dato è errato, carica tutti i film precedenti e lancia un errore.

#### saveToFile
- salva in base ad un certo ordine:
    - ABR \- segue un ordine "in-visita";
    - ListaCollegataNonOrdinata \- segue l'ordine di inserimento;

#### clear
- cancella tutti i dati nel database.
- seleziona come dizionario ABR e come algoritmo di ordinamento BubbleSort.

#### countPeople
- conta sia gli attori che i registi.

#### getMovieByTitle
- l'input deve essere il titolo esatto contenente nel database
- se l'input non esiste, ritorna null

#### getPersonByName
- l'input deve essere il nome (e cognome) esatto contenente nel database
- se l'input non esiste, ritorna null

#### getAllMovies
- salva in base ad un certo ordine:
    - ABR \- segue un ordine "in-visita";
    - ListaNonOrdinata \- segue l'ordine di inserimento;
#### getAllPeople
- salva in base ad un certo ordine:
    - ABR \- segue un ordine "in-visita";
    - ListaNonOrdinata \- segue l'ordine di inserimento;

### IMovidaSearch
- l'input se è una stringa è key sensitive.
- l'input deve avere lo stesso nome dell'oggetto che si ricerca, altrimenti non lo si trova.
- solo searchMostVotedMovies, searcMostRecentMovies, searchMostActiveActors usano l'algoritmo di ordinamento, le altre ricerche fanno una scansione lineare.

#### searchMovieByTitle
- solo in questo caso si cerca tutti i film con una certa stringa.

#### searchMoviesDirectedBy
- l'input deve essere il nome (e cognome) esatto contenente nel database
- se l'input non esiste, ritorna null

#### searchMoviesStarredBy
- l'input deve essere il nome (e cognome) esatto contenente nel database
- se l'input non esiste, ritorna null
