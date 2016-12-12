#!/bin/env groovy

def call(body) {
	// evaluate the body block, and collect configuration into the object
	def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    stage ("Checkout Code") {
    	checkout scm
    }

    stage ("Testing ${config.APP_NAME}") {
    	withDockerContainer(image: 'java8') {
    		sh "gradle clean compileJava"
    	}
    }
}
