# Knife Visuals

This project is a Fabric mod starter for Minecraft 1.21.1 that adds a lightweight visual-only sword replacement system.

What it includes:
- Toggle to enable/disable the mod
- Style selection: normal, karambit, butterfly
- Config keybind and inspect keybind exposed in Minecraft controls
- Sword model predicates so diamond and netherite swords can swap between visual styles at runtime

Important:
This version is intentionally a best-effort skeleton. The exact CSGO knife geometry, sounds, and animations require custom 3D models, custom resource assets, and additional animation work. This code gives the mod framework and GUI so you can plug in real knife assets later.

Quick setup:
1. Install JDK 21
2. Install Gradle or use the wrapper after generating it locally
3. Run `gradle build`
4. Put the built JAR into your Fabric mods folder
5. Open Minecraft and bind the keys in Controls

Default controls:
- Open GUI: `O`
- Toggle inspect state: `G`

The config screen lets you choose:
- Normal sword
- Karambit
- Butterfly
- Enable/disable overall mod

You can replace the placeholder item models in `src/main/resources/assets/knifevisuals/models/item` with your real knife models from Blockbench or a custom resource pack.
