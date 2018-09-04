jacoco-plugin
=============

This is an adaptation of the Jacoco plugin for Jenkins. 
Instead of covering test code, the coverage of transitions, activities and workflows for the TREND framework is calculated. 
This modified plugin is no longer usable for code coverage.


How to build and test
=====================

* Build the plugin:

`mvn package`

* Test locally (invokes a local Jenkins instance with the plugin installed):

`mvn hpi:run`

See https://jenkinsci.github.io/maven-hpi-plugin/ for details.

Hosting
=====================
The project is hosted at github: https://github.com/GEBIT/jacoco-plugin


New Releases
=====================
A release is identified by a git tag. The rest is done with github: https://help.github.com/articles/creating-releases/
Don't forget to upload the hpi artifact to the release. 




