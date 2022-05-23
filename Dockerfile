FROM docker-registry-default.tron.hl.ayo.co.za/ayo-dev/centos-splunk:openjdk-8
ARG APPLICATION_MAIN
ENV APPLICATION_MAIN_ENV=${APPLICATION_MAIN}
ARG PROJECT_ROOT
COPY ${PROJECT_ROOT}/target/dependency/BOOT-INF/lib /app/lib
COPY ${PROJECT_ROOT}/target/dependency/META-INF /app/META-INF
COPY ${PROJECT_ROOT}/target/dependency/BOOT-INF/classes /app
RUN chmod -R +x /app/za