Intellij-smtp-server
====================

Mail server plugin for Intellij Idea.
If your code is sending or receiving e-mails this plugin will provide lightweight SMTP/POP3 services inside the IDE to test your application.

How to build from source with Gradle
=================================

1. First of all make sure you hae Gradle installed
2. From gradle console execute "gradle dist"
3. Navigate to {$Project_dir}/build and find plugin assembled as ZIP archive
4. Launch Intellij Idea, navigate to Settings -> Plugins -> Install plugin from disk
5. Choose zip archive assembled at step (3)
6. Restart Idea and enjoy the plugin running

How to build from source with Intellij Idea
=================================

You need to execute the following instructions once. Having a project set up correctly you can launch the plugin in one click anytime.

1. Create an idea project for the plugin code. You may need Gradle and Cotlin plugins
2. Use "import from Gradle" to create project module
3. Open .iml file and change module type to PLUGIN_MODULE, then reload the project
4. Also make sure you have the following component in the .iml file: 
   <component name="DevKit.ModuleBuildProperties" url="file://$MODULE_DIR$/META-INF/plugin.xml" />
5. Now Idea recognizes your module as a plugin module, so you can create a dedicated plugin run configuration. Select any idea installation you like as an SDK. Community edition is by far the fastest one.
6. If you've configured Gradle properly your module should have all dependencies installed via Gradle. Go to module dependencies dialog and remove the following dependencies: gradle:openapi, grandle:utils, gradle:annotations, gradle:extensions. These libraries are part of Idea SDK, you don't need them when building the plugin in Idea. 
7. Now you can press run button |> and enjoy the plugin running
