NorthernStars Mixed-Reality Framework
=====================================
The NorthernStars Mixed-Reality Framework (MR_NSFW) is a java-based framework for developing an artificial intelligence for the mixed-reality system.
The Mixed-Reality is a robot soccer league that plays with a set of tiny robots an a simulated soccer field.

The robots are controlled remotely by game server wich interacts with several autonomous agents (clients).
This framework gives you the aibility for developing your own agent.

Install Instructions
====================
This install instructions explains how to import MR_NSFW into Eclipse (www.eclipse.org).
The repository includes several eclipse subprojects that can be imported automatically


1. Import projects from git repository with EGit (for example).
2. Run build.xml in bot_essentials with ant (Right click on build.xml > Run as > Ant Build)
3. Run build.xml in bot_network with ant 
4. Run build.xml in bot_core with ant
5. Run build.xml in bot_mrlib with ant

How-To start example AI
=======================
The repository also includes an example AI you can start to test the framework.

Start the Mixed-Reality System (maybe inside a VM) and than create a run configuration inside eclipse
for the main() function inside bot_core.

To start the example AI you have to add some arguments:
```
-bn <RID> -tn "<TEAMNAME>" -t <TEAMCOLOR> -ids <VID> -s <SERVERIP>:<SERVERPORT> -aiarc "<AIARC>" -aicl "<AICL>" -aiarg <AIARGS>
```
The agruments are (in brackets are values for testing):
- RID: Robot control ID [0]
- TEAMNAME: Name of your team [Test]
- TEAMCOLOR: blau or gelb (for blue or yellow) teamcolor [blau]
- VID: Vision control id [0]
- SERVERIP: Ip address of the Mixed-Reality System game server
- SERVERPORT: Port of the team color you want to connect to. (3310 for blue; 3311 for yellow) [3310] 
- AIARC: Localtion of your AI inside your workspace [${workspace_loc:example_ai}/bin]
- AICL: Classpath of your AI.java that includes your AI [exampleai.brain.AI]
- AIARG: Arguments that are passed to your AI [0]

Replace the arguments (including the < and >) with your settings and run the configuration.
Maybe you get somethign like this:
```
-bn 0 -tn "Test" -t blau -ids 0 -s 192.168.178.22:3310 -aiarc "${workspace_loc:example_ai}/bin" -aicl "exampleai.brain.AI" -aiarg 0
```


LICENCE
=======
This framework is published unter the creative commons licence (CC BY-NC-SA 3.0).

See: http://creativecommons.org/licenses/by-nc-sa/3.0/legalcode for more information.

The main things of the licence are:

- Attribution — You must attribute the work in the manner specified by the author or licensor (but not in any way that suggests that they endorse you or your use of the work). 
- Noncommercial — You may not use this work for commercial purposes. 
- Share Alike — If you alter, transform, or build upon this work, you may distribute the resulting work only under the same or similar license to this one. 
