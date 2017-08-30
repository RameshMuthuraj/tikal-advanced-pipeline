package com.tikalk.ci.gradle;

import com.tikalk.ci.BasePipeline

class GradlePipeline extends BasePipeline {
    boolean debugMode
    int waitForInputTimeout
    def buildTarget


    GradlePipeline(script) {
        super(script)
    }


    @Override
    void populateBuildDisplayName() {
        script.currentBuild.displayName = "${script.currentBuild.displayName} ${retrieveCurrentBuildUser()}"
    }

    void retrieveCurrentBuildUser() {
        script.wrap([$class: 'BuildUser']) {
            script.sh returnStdout: true, script: 'echo ${BUILD_USER}'
        }
    }

    @Override
    void initParams()
    {
        gitCredentialsId = script.params.gitCredentialsId //script.params.// Implement to set params that are not able to set in constructor (due to @NonCPS etc)
        gitRepoUrl  = script.params.gitRepoUrl
        buildTarget = script.params.buildTarget //script.params.// Implement to set params that are not able to set in constructor (due to @NonCPS etc)
        dockerHost  = "localhost"
    }
    @Override
    void build() {
        logger.info "Implements gradle build here"
        script.sh "ls"
        script.sh "./gradlew $buildTarget -PdockerHost=$dockerHost"
    }


    void waitForInput() {
        script.timeout(time: waitForInputTimeout, unit: 'MINUTES') {
            if (debugMode) {
                script.input 'Continue with cleanup?'
            }
        }
    }



    void compile(Map m) {

    }

    void buildDocker() {

    }





}
