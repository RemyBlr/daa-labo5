
# Introduction
TODO

---

## Exercice 2

### Choix d'implémentation

Pour le squelette de l'Activité, les choix suivant ont été faits :
1. Utilisation d'un `RecyclerView` et d'un `GridLayoutManager`: Utilisation du `GridLayoutManager`avec 3 colonnes. Et le `RecyclerView` permet une gestion efficace des grandes listes d'éléments avec un défilement fluide.
2. CardView pour chaque élément de la grille :
    - Permet d'encapsuler chaque image dans une carte.
    - Possibilité d'ajouter des ombres et des coins arrondis pour un meilleur rendu visuel.
    - Style et espacement constant entre les éléments.
3. Menu : Ajout d'un menu dans la barre d'action pour pouvoir vider le cache. Création d'un fichier XML dans le dossier `res/menu` pour définir les options du menu.
4. Utilisation d'un `ImageAdapter` pour gérer l'affichage des images dans le `RecyclerView` et le peuplement des vues.

### Tests effectués
| Titre du test               | Description du test                                                        | Résultat obtenu |
|-----------------------------|----------------------------------------------------------------------------|-----------------|
| Affichage de la grille      | Un `RecyclerView` afficher une grille de 3 colonnes.                       | OK              |
| Layout                      | Lors de la rotation de l'écran, la grille conserve ses 3 colonnes.         | OK              |
| Affichage du bouton de menu | Le bouton de menu apparaît dans la barre d'action.                         | OK              |
| Défilement fluide           | Le défilement de la grille est fluide, même avec un grand nombre d'images. | OK              |
| Affichage d'une carte       | Chaque élélement de la grille est affiché avec une progressBar qui tourne. | OK              |

---

## Exercice 3

### Choix d'implémentation
1. Gestion des images :
    - Séparation des occupations : Les différentes opération sont séparées en méthodes suspensives distinctes pour une meilleure lisibilité et maintenabilité.
2. Gestion du cache :
   - Vérification du temps du fichier avant de l'utiliser.
   - Format du nom du fichier (image_<id>.jpg) pour une identification facile.
   - Utilisation du `cacheDir` de l'application pour stocker les images mises en cache.
   - Utilisation de `lastModified` pour vérifier si le fichier est expiré.
3. Dispatchers :
    - Utilisation de `Dispatchers.IO` pour les opérations de téléchargement et de lecture/écriture sur le cache.
    - Utilisation de `Dispatchers.Main` pour les mises à jour de l'UI.
    - Utilisation de `Dispatchers.Default` pour les opérations de décodage d'images.
4. Cycle de vie des coroutines :
    - Utilisation de `lifecycleScope` pour lier les coroutines au cycle de vie de l'Activity. Et ainsi garantir l'arrêt auto des coroutines lorsque l'Activity est détruite.
    - `ViewHolder` utilise `CoroutineScope(Dispatchers.Main)` pour lancer des coroutines liées à la vue.
    - Annulation des coroutines dans `onViewRecycled` pour éviter les fuites de mémoire en stopant les tâches en cours lorsque la vue est recyclée.

### Tests effectués
| Titre du test                  | Description du test                                                                           | Résultat obtenu |
|--------------------------------|-----------------------------------------------------------------------------------------------|-----------------|
| Premier lancerment             | Lancer l'application pour la première fois, les images doivent être téléchargées.             | OK              |
| ProgressBar lors du chargement | Vérifier que la ProgressBar s'affiche pendant le téléchargement des images.                   | OK              |
| Images après téléchargement    | Vérifier que les images s'affichent correctement après le téléchargement.                     | OK              |
| Deuxième lancement             | Relancer l'application, les images doivent être chargées depuis le cache.                     | OK              |
| Après 5 minutes                | Attendre plus de 5 minutes et relancer l'application, les images doivent être retéléchargées. | OK              |
| Rotation de l'écran            | Faire une rotation de l'écran et vérifier que les images restent affichées correctement.      | OK              |


### Réponses aux questions

#### 3.1 Arrêt des coroutines lors du recyclage des vues
TODO

#### 3.2 Arrêt des coroutines lors de la destruction de l'Activity
TODO

#### 3.3 Différences entre les Dispatchers
TODO

#### 3.4 Gestion des clicks sur les images
TODO

---

## Exercice 4

### Choix d'implémentation
TODO

### Tests effectués
TODO

### Réponses aux questions
TODO

---

# Conclusion
TODO