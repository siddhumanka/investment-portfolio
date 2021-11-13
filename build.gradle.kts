import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.6"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.31"
	kotlin("plugin.spring") version "1.5.31"
}

group = "de.vitamin"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign:2.1.3.RELEASE")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.fasterxml.jackson.core:jackson-core:2.12.3")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.12.3")
	implementation("com.auth0:java-jwt:3.10.3")

	testImplementation("com.github.tomakehurst:wiremock-jre8:2.26.3")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
	testImplementation("org.mockito:mockito-junit-jupiter")
	testImplementation("org.mockito:mockito-core:3.5.7")

	testRuntimeOnly("org.junit.jupiter:junit-jupiter:5.6.2")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
