# VOLONTAIREMENT VULNERABLE - support du workshop Trivy, ne jamais deployer.
# Defauts voulus : image de base ancienne et depreciee, tag flottant, execution en root,
# pas de HEALTHCHECK, faux secrets ecrits dans l'image.
FROM openjdk:17-jdk-slim

WORKDIR /app
COPY target/*.war /app/app.war

# Faux secrets assembles au build : rien de detectable dans le depot Git
RUN printf 'AWS_ACCESS_KEY_ID=%s%s\nAWS_SECRET_ACCESS_KEY=%s%s\n' \
      "AKIA" "Z7XQ4WLB5JKTMNPR" \
      "aB3dE5fG7hJ9kL1mN3pQ" "5rS7tU9vW1xY3zA5bC7d" > /app/config.env
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app/app.war"]