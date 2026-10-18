# Workshop 2 — Dependabot & OWASP Dependency-Check

**Groupe 4 : Azzeddine Salmoun , Wail Chairi Mahjor et Toufik Kharkhor**  
**Projet analysé :** FPLAcademy — application Maven / Spring Boot  
**Branche de travail :** `workshop2/dependency-security`

## 1. Objectif

Ce workshop a pour objectif de sécuriser les dépendances d’un projet Maven et d’intégrer les contrôles dans un processus CI/CD.

- **OWASP Dependency-Check** analyse les dépendances et recherche les vulnérabilités connues.
- **Dependabot Version Updates** propose des mises à jour de versions sous forme de pull requests.
- **Dependabot Alerts** signale les dépendances associées à des vulnérabilités connues.
- **Dependabot Security Updates** peut proposer des pull requests de correction lorsqu’un correctif est disponible.
- **GitHub Actions** automatise l’analyse des dépendances dans le workflow CI.

## 2. Configuration OWASP Dependency-Check

Le plugin Maven `org.owasp:dependency-check-maven:12.2.2` a été ajouté dans `pom.xml`. Le scan local a été exécuté avec le Maven Wrapper :

```powershell
.\mvnw.cmd org.owasp:dependency-check-maven:12.2.2:check
```

Le rapport HTML a été généré à l’emplacement :

```text
target/dependency-check-report.html
```

### Résultats du scan local

| Indicateur | Résultat |
|---|---:|
| Dépendances analysées | 94 (52 uniques) |
| Dépendances vulnérables | 11 |
| Vulnérabilités détectées | 155 |
| Vulnérabilités supprimées | 0 |
| Version Dependency-Check | 12.2.2 |
| Résultat Maven local | `BUILD SUCCESS` |

Les composants signalés dans le résumé comprennent notamment des bibliothèques Spring, Spring Security, Tomcat et Jackson. Les résultats doivent être examinés avant de décider quelles versions mettre à jour ; les comptes affichés par l’outil ne suffisent pas à conclure qu’une vulnérabilité est exploitable dans tous les contextes.

### Captures du scan

<img width="771" height="519" alt="01-rapport-dependency-check" src="https://github.com/user-attachments/assets/d77aa951-f821-461c-bbe5-eaba9b3dd25e" />

*Figure 1 — Informations du scan local et statistiques globales.*

<img width="1864" height="804" alt="02-resume-dependances-vulnerables" src="https://github.com/user-attachments/assets/6dbad642-ef74-46fc-9170-7279defdf4a8" />


*Figure 2 — Extrait du résumé des dépendances vulnérables et de leurs niveaux de sévérité.*

## 3. Configuration de Dependabot

Le fichier `.github/dependabot.yml` configure les vérifications périodiques des dépendances Maven et des actions GitHub. La configuration utilisée pour le projet est la suivante :

```yaml
version: 2
updates:
  - package-ecosystem: "maven"
    directory: "/"
    schedule:
      interval: "weekly"
      day: "thursday"
      time: "19:00"
      timezone: "Africa/Casablanca"
    open-pull-requests-limit: 5
    allow:
      - dependency-type: "direct"
      - dependency-type: "indirect"

  - package-ecosystem: "github-actions"
    directory: "/"
    schedule:
      interval: "weekly"
```

Cette configuration permet à Dependabot de vérifier les mises à jour Maven et GitHub Actions. Les propositions sont soumises sous forme de pull requests pour permettre une revue avant fusion.

### Alertes de sécurité

Dans les paramètres de sécurité du dépôt GitHub, les sections **Dependabot alerts** et **Dependabot security updates** affichaient un bouton **Disable**, indiquant que ces fonctionnalités étaient activées au moment de la vérification.

<img width="1138" height="504" alt="03-dependabot-alerts-security-updates" src="https://github.com/user-attachments/assets/faca9102-34e7-4e90-9eb6-6015a8ee5365" />


*Figure 3 — Paramètres de sécurité Dependabot.*

## 4. Pull requests générées automatiquement

Dependabot a généré plusieurs pull requests de mise à jour, portant notamment sur Maven, OWASP Dependency-Check, Spring Boot et des actions GitHub. Cela confirme que les mises à jour de versions sont prises en compte.

Les pull requests doivent être examinées individuellement. Elles ne doivent pas être fusionnées automatiquement : il faut vérifier les changements, la compatibilité avec le projet et le résultat des contrôles CI.

<img width="668" height="439" alt="04-pull-requests-dependabot" src="https://github.com/user-attachments/assets/e075fc07-e1f0-4d3d-87e6-44805d11be77" />


*Figure 4 — Exemples de pull requests proposées par Dependabot.*

## 5. Intégration CI/CD avec GitHub Actions

Le workflow se trouve dans :

```text
.github/workflows/dependency-check.yml
```

Il est configuré pour se déclencher lors d’un push, d’une pull request ciblant les branches concernées, ou manuellement. Il récupère le dépôt, configure Java, exécute le Maven Wrapper et tente de publier le rapport HTML comme artefact GitHub Actions.

Commande d’analyse exécutée dans le workflow :

```bash
./mvnw org.owasp:dependency-check-maven:12.2.2:check
```

### Preuve d’exécution à compléter




## 6. Conclusion

Ce workshop a permis de mettre en place une analyse des dépendances avec OWASP Dependency-Check, la surveillance des mises à jour avec Dependabot et un workflow GitHub Actions pour automatiser l’analyse. Le scan local a détecté 11 dépendances vulnérables et 155 vulnérabilités parmi 94 dépendances analysées. Dependabot a proposé des mises à jour sous forme de pull requests.

