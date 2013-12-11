Intellij-smtp-server
====================

SMTP server plugin for Intellij Idea


How to build and launch with Idea
=================================

You need to execute the following instructions once. Having a project set up correctly you can launch the plugin in one click anytime.

1. Create an idea project for the plugin code. You may need Gradle and Cotlin plugins
2. Use "import from Gradle" to create project module
3. Open .iml file and change module type to PLUGIN_MODULE, then reload the project
4. Now Idea recognizes your module as a plugin module, so you can create a dedicated plugin run configuration. Select any idea installation you like as an SDK. Community edition is by far the fastest one.
5. If you've configured Gradle properly your module should have all dependencies installed via Gradle. Go to module dependencies dialog and remove the following dependencies: gradle:openapi, grandle:utils, gradle:annotations, gradle:extensions. These libraries are part of Idea SDK, you don't need them when building the plugin in Idea. 
6. Now you can press run button |> and enjoy the plugin running
