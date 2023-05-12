# Configuration de l'IDE

* SDK: openjdk-20
* Language-level: 20 - No new language features
* Libraries: `./libraies/core-3.5.1.jar` & `./libraies/javase-3.5.1.jar`

Voir la section suivante afin de télécharger les librairies.

# Générer les librairies

### Installation rapide
```bash
if [ ! -f zxing-3.5.1.zip ]; then
    echo "Téléchargement de la release"
    wget https://codeload.github.com/zxing/zxing/zip/refs/tags/zxing-3.5.1 -O zxing-3.5.1.zip
fi
if [ ! -d zxing-zxing-3.5.1 ]; then
    echo "Dézip de la release"
    unzip zxing-3.5.1.zip
fi

if [ ! -f librairies/core-3.5.1.jar ]; then
    echo "Génération de la librairie core-3.5.1.jar"
    cd zxing-zxing-3.5.1/core
    mvn install
    cp target/core-3.5.1.jar ../../librairies
    cd ../..
fi
if [ ! -f librairies/javase-3.5.1.jar ]; then
    echo "Génération de la librairie javase-3.5.1.jar"
    cd zxing-zxing-3.5.1/javase
    mvn install
    cp target/javase-3.5.1.jar ../../librairies
fi
```

### Récupérer la release du projet zxing
A télécharger ici: `https://github.com/zxing/zxing/releases/tag/zxing-3.5.1`.

### Générer la librairie core-3.5.1.jar

Lancer dans le dossier `core` la commande:
```bash
mvn install
```
Cela va générer la librairie `core/target/core-3.5.1.jar` qu'il faut déplacer dans le dossier `./libraies` du projet

### Générer la librairie javase-3.5.1.jar

Lancer dans le dossier `javase` la commande:
```bash
mvn install
```
Cela va générer la librairie `core/target/javase-3.5.1.jar` qu'il faut déplacer dans le dossier `./libraies` du projet
