Questa cartella è riservata alle librerie esterne.

Le dipendenze del progetto TheKnife sono gestite interamente da Apache Maven
(file pom.xml nella radice del progetto) e vengono scaricate automaticamente
dal repository Maven Central durante la compilazione.

Non è necessario copiare manualmente alcun file JAR in questa cartella.

Dipendenze principali (dichiarate nel pom.xml):
  - postgresql-42.7.3.jar   Driver JDBC per PostgreSQL
  - junit-jupiter-5.10.2    Framework di test (scope: test)

Per scaricare le dipendenze senza compilare:
  mvn dependency:resolve