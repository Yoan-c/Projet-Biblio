# Projet-Biblio

CONTEXT <br/>
La covid19 a obligé de nombreuses entreprises non seulement à se mettre au numérique mais également à améliorer leurs services existants. C’est le cas d’une bibliothèque 
située à Pontault-Combault, qui souhaite moderniser son système de gestion d’emprunt de livres.

Ce projet comporte l'API (JAVA), le front-end (HTML/JS/CSS), un batch de relance codé en JAVA.<br/>

Quelques prérequis pour installer et utiliser l'API :<br/>
- Avoir un JDK d'installer (minimum le 18) <br/>
- Un IDE (eclipse, intelliJ ...)<br/>
- Un logiciel tel que XAMPP qui réunit un serveur Apache, Mysql ( le minimum pour faire fonctionner la BDD et le front)

<h2>Installation et configuration</h2>
<h3>API et front</h3>
1) Télécharger le projet <br/>
2) Démarrer le serveur Apache et Mysql <br/>
3) Importer le fichier <em>BiblioStudiDB.sql</em> qui se trouve dans /Biblio/Bdd dans le serveur SQL <br/>
4) Mettre le dossier "Front" sur le serveur Apache <br/>
5) Ouvrir le dossier Biblio avec votre IDE et configurer le fichier application.properties <br/>
6) Faire un Maven clean install (afin de nettoyer et télécharger tout les package pour le projet) <br/>
7) Lancer l'application avec springBoot afin de démarrer l'API. Il existe 3 compte pour se connecter (username : test@gmail.com, password : test) les autres sont visibles dans la table utilisateur 
<br/>
<h3>Batch de relance</h3>
1) Configurer le fichier application.propreties (mettre votre connexion d'adresse mail <strong> ATTENTION</strong> une mise à jour à été effectué, il faut sécuriser votre compte mail (authentification à deux facteur) et creer un mot de passe d'application qu'il faut utiliser en tant que password. <br/>
2) Modifier la fonction JsonItemReader dans le fichier "SpringBatchConfig.java" et inséré en tant que ressource , le lien URL qui permet de faire la requête get /batch à l'API . Cette requête prend en argument un username et password qui est admin admin. En Local, il n'est pas nécessaire de changer 
ces paramètres. <br/>
3) Lancer Maven clean install afin de nettoyer et installer les packages. <br/>
4) Executer le programme. Assurer vous que le serveur sur lequel se trouve l'API soit bien démarré , sinon le batch ne fonctionnera pas. <br/>
5) Une fois lancé, les adresses mail qui s'affichent dans le terminal, auront reçu un mail de relance. <br/>
