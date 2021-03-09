# Variables

JFLAGS = -g
JC = javac
JVM = java
CLASSES = \
	movida/hanchu/MovidaCompareException.java \
	movida/hanchu/ComparableType.java \
	movida/commons/Movie.java \
	movida/commons/Person.java \
	movida/commons/IMovidaCollaborations.java \
	movida/commons/Collaboration.java \
	movida/commons/IMovidaConfig.java \
	movida/commons/MapImplementation.java \
	movida/commons/MovidaFileException.java \
	movida/commons/SortingAlgorithm.java \
	movida/hanchu/ABR.java \
	movida/hanchu/ABRNodo.java \
	movida/hanchu/BubbleSort.java \
	movida/hanchu/CollaborationComparator.java \
	movida/hanchu/Dizionario.java \
	movida/hanchu/Grafo.java \
	movida/hanchu/ListaNonOrdinata.java \
	movida/hanchu/ListaNonOrdinataNodo.java \
	movida/hanchu/MergeSort.java \
	movida/hanchu/MovidaCore.java \
	movida/hanchu/Ordinamento.java \
	movida/hanchu/QueueElement.java \
	movida/hanchu/_Test.java
MAIN = movida/hanchu/_Test

.SUFFIXES: .java .class

default: classes
classes: $(CLASSES:.java=.class)

# Targets

.java.class:
	$(JC) $(JFLAGS) $*.java

run: $(MAIN).class
	$(JVM) $(MAIN)

clean:
	$(RM) movida/hanchu/*.class movida/commons/*.class
