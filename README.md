# Gun Mayhem 3

* This project acts as a remake/sequel of the popular shooter Gun Mayhem 2. It is a platformer / shooter played against one or several bots, where the goal is to knock your opponents of the map using knockback from your guns. We decided on this game because of its complexity, fun-factor and a sprinkle of nostalgia.

---
### Running the project
* You can run the project with Maven using *mvn exec:java*
* You will need Java 25.
* If Maven is not installed locally on your PC, you can run the main class directly through your IDE:

      <main.class>inf112.mayhem.app.Main</main.class>

* Running the program should open a new window. Exit by pressing the Exit-button or by closing the window.

* You may have to compile first, with mvn compile – or in a single step, *mvn compile exec:java*


* To run the tests, use: *mvn test*
* If Maven is not installed locally, you can run the tests through the test package with your IDE.

---
### Controls
- Controls for the game can be found by pressing the **Options** button in the main menu.

---
### Security

* As of now, there are no security risks us developers are aware of.
* If found, send any of the devs a message through our UIB mails.

---
### Notable bugs:
* Out of ammo weapons may occasionally bug other weapons of similar type when trying to pick them up.
* Player may spawn on an area which lands them directly into the void on level 3 if controller does not move first few seconds from spawning.

**Other bugs may be reported as a message to any of the devs through our UIB mails.**

---
### Structure
* Notable parts of the code are found under team-mayhem [gun-mayhem-3] / src
* https://git.app.uib.no/inf112/26v/proj/team-mayhem/-/blob/c15a76c5179f7e3a7cdebe86ac8216acbcf7241f/src

---
### Contributing
* How to contribute code is found under [Contributing](CONTRIBUTING.md)

---
### License
* Licensing is found under [License](LICENSE.md)

---

---
## Credits

### Authors
* **Adrian Bliktun Strand**
* **Malik Andreas Cissé**
* **Alexander Wurm Møster**
* **Martin Andreas Drevdal**
* **Jon Hauge**
* **Sverre Kristian Legria Thune**

### Contributors
* Certain parts of code were helped by users on StackOverflow. Relevant examples are commented within the code itself.
* Other parts of the code in the project was formulated / helped by AI/LLMs, such as OpenAI's ChatGPT and Anthropic's ClaudeAI.
* All code helped by AI is understood in and of itself and in relation to the rest of the project.


* StackOverflow - https://stackoverflow.com/
* OpenAI - https://chatgpt.com - first retrieved 09.02.2026
* Anthropic - https://chat.chaton.ai - first retrieved 09.02.2025

### Sound and Graphics

- `src/main/resources/character_sprites` – JoyceQuan, GitHub Repository, https://github.com/Randonee1/Gun-Mayhem-master
- `src/main/resources/weapons` - JoyceQuan, GitHub Repository, https://github.com/Randonee1/Gun-Mayhem-master
- `src/main/resources/fonts/DejaVuSans.ttf` - Bitstream Inc - https://dejavu-fonts.github.io