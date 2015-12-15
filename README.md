![Accumulocraft](accumulocraft.png)

## Why

Accumulo + Minecraft = More awesome than the [Accumulo Monitor](https://accumulo.apache.org/screenshots.html)

## Compile

Compile the Accumulocraft plugin using Maven

```
mvn install
```

## Server

Download the [SpongeVanilla](https://repo.spongepowered.org/maven/org/spongepowered/spongevanilla/) server into the 
servers/spongevanilla .

Launch the server

```
java -Xmx1024M -Xms1024M -jar servers/spongevanilla/spongevanilla-<VERSION>.jar nogui
```

The server version used `1.8-2.1-DEV-91` during development.

When you start the server for the first time, it will quit because you haven't accepted the EULA. Stop the server
and edit spongevanilla/eula.txt to accept the eula. Or just do this

```
cat "eula=true" > servers/spongevanilla/eula.txt
```

## Install plugin

Link the plugin jar into the server mods directory

```
ln -s target/accumulocraft-1.0-SNAPSHOT.jar servers/spongevanilla/mods/accumulocraft-1.0-SNAPSHOT.jar
```

Add a config file to servers/spongevanilla/config/accumulocraft/accumulocraft.conf

```
accumulocraft {
  minicluster=true
  zk_hosts="localhost:2181"
  instance=accumulocraft
  username=root
  password=secret
}
```

## Client

The official [Minecraft client](https://minecraft.net/download) will work. You'll need
to login/register and change to a paid account. Then you'll be able to join a Multiplayer game using a server with the
Accumulocraft plugin installed.

## Resources

This plugin is based on [Sponge](https://www.spongepowered.org/)
