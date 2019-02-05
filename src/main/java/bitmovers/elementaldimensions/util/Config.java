package bitmovers.elementaldimensions.util;


import elec332.core.api.config.Configurable;

/**
 * Created by Elec332 on 4-8-2016.
 */
public class Config {

    @Configurable.Class
    public static class General {

    }

    @Configurable.Class
    public static class Client { //Maybe

    }

    @Configurable.Class(comment = "Wand settings")
    public static class Wand {

        @Configurable(comment = "Maximum number of dust to fully charge a wand", minValue = 0, maxValue = 10000000)
        public static int maxDust = 512;

        @Configurable(comment = "Dust usage for the damage focus", minValue = 0, maxValue = 10000000)
        public static int damageDustUsage = 4;
        @Configurable(comment = "Dust usage for the digging focus", minValue = 0, maxValue = 10000000)
        public static int diggingDustUsage = 1;
        @Configurable(comment = "Dust usage for the teleport focus", minValue = 0, maxValue = 10000000)
        public static int teleportDustUsage = 2;
    }

    @Configurable.Class(comment = "Mob settings")
    public static class Mobs {

        @Configurable(comment = "Max number of guards around a portal dungeon", minValue = 0, maxValue = 100)
        public static int maxGuards = 4;

        @Configurable(comment = "Max number of guards can spawn in a portal dungeon before it 'runs out'", minValue = 0, maxValue = 100000)
        public static int totalMaxGuards = 100;
    }

    @Configurable.Class(comment = "Dimension settings")
    public static class Dimensions {

        @Configurable(comment = "Chance for a portal dungeon to spawn (1 means 100%)", maxValue = 1.0f)
        public static float portalDungeonChance = 0.003f;

        @Configurable(comment = "Chance for an earth dungeon to spawn (in the earth dimension) (1 means 100%)", maxValue = 1.0f)
        public static float earthDungeonChance = 0.03f;

        @Configurable(comment = "Chance for an earth tower to spawn (in the earth dimension) (1 means 100%)", maxValue = 1.0f)
        public static float earthTowerChance = 0.01f;

        @Configurable(comment = "Chance for an air dungeon to spawn (in the air dimension) (1 means 100%)", maxValue = 1.0f)
        public static float airDungeonChance = 0.03f;

        @Configurable(comment = "Chance for a water dungeon to spawn (in the water dimension) (1 means 100%)", maxValue = 1.0f)
        public static float waterDungeonChance = 0.02f;

        @Configurable(comment = "Chance for a spirit dungeon to spawn (in the spirit dimension) (1 means 100%)", maxValue = 1.0f)
        public static float spiritDungeonChance = 0.02f;

        @Configurable(comment = "Strength of the wind in the air dimension)", maxValue = 1.0f)
        public static float windStrength = 0.003f;

        @Configurable.Class(comment = "Settings for the Air dimension.")
        public static class Air {

            @Configurable(comment = "Dimension ID for the dimension", minValue = 2, maxValue = 1000)
            public static int dimensionID = 102;

        }

        @Configurable.Class(comment = "Settings for the Earth dimension.")
        public static class Earth {

            @Configurable(comment = "Dimension ID for the dimension", minValue = 2, maxValue = 1000)
            public static int dimensionID = 100;

        }

        @Configurable.Class(comment = "Settings for the Fire dimension.")
        public static class Fire {

            @Configurable(comment = "Dimension ID for the dimension", minValue = 2, maxValue = 1000)
            public static int dimensionID = 104;

        }

        @Configurable.Class(comment = "Settings for the Spirit dimension.")
        public static class Spirit {

            @Configurable(comment = "Dimension ID for the dimension", minValue = 2, maxValue = 1000)
            public static int dimensionID = 103;

        }

        @Configurable.Class(comment = "Settings for the Water dimension.")
        public static class Water {

            @Configurable(comment = "Dimension ID for the dimension", minValue = 2, maxValue = 1000)
            public static int dimensionID = 101;

        }

    }

}
