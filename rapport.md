
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

### Tests effectués

### Réponese aux questions

---

## Exercice 4

### Choix d'implémentation
TODO

### Tests effectués
TODO

### Réponese aux questions
TODO

---

# Conclusion
TODO