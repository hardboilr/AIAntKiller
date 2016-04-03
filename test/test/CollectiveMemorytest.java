package test;

import aiantwars.EAntType;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import aiantwars.ITeamInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import memory.CollectiveMemory;
import memory.Position;
import memory.Tile;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

public class CollectiveMemorytest {

    private static CollectiveMemory memory;

    @BeforeClass
    public static void before() {
        memory = CollectiveMemory.getInstance();

        List<ILocationInfo> locations = new ArrayList();

        ILocationInfo location1 = new ILocationInfo() {
            @Override
            public int getX() {
                return 0;
            }

            @Override
            public int getY() {
                return 0;
            }

            @Override
            public IAntInfo getAnt() {
                return null;
            }

            @Override
            public boolean isFilled() {
                return false;
            }

            @Override
            public boolean isRock() {
                return false;
            }

            @Override
            public int getFoodCount() {
                return 2;
            }
        };

        ILocationInfo location2 = new ILocationInfo() {
            @Override
            public int getX() {
                return 0;
            }

            @Override
            public int getY() {
                return 1;
            }

            @Override
            public IAntInfo getAnt() {
                return null;
            }

            @Override
            public boolean isFilled() {
                return false;
            }

            @Override
            public boolean isRock() {
                return false;
            }

            @Override
            public int getFoodCount() {
                return 0;
            }
        };

        ILocationInfo location3 = new ILocationInfo() {
            @Override
            public int getX() {
                return 0;
            }

            @Override
            public int getY() {
                return 2;
            }

            @Override
            public IAntInfo getAnt() {
                return null;
            }

            @Override
            public boolean isFilled() {
                return false;
            }

            @Override
            public boolean isRock() {
                return false;
            }

            @Override
            public int getFoodCount() {
                return 5;
            }
        };

        locations.add(location1);
        locations.add(location2);
        locations.add(location3);

        memory.addVisibleLocations(locations);

        IAntInfo ant = new IAntInfo() {
            @Override
            public int antID() {
                return 1;
            }

            @Override
            public ITeamInfo getTeamInfo() {
                ITeamInfo team = new ITeamInfo() {
                    @Override
                    public int getTeamID() {
                        return 1;
                    }

                    @Override
                    public String getTeamName() {
                        return "Dream Team";
                    }
                };
                return team;
            }

            @Override
            public EAntType getAntType() {
                return EAntType.QUEEN;
            }

            @Override
            public ILocationInfo getLocation() {
                return location1;
            }

            @Override
            public int getDirection() {
                return 1;
            }

            @Override
            public boolean carriesSoil() {
                return false;
            }

            @Override
            public int getAge() {
                return 2;
            }

            @Override
            public int getHitPoints() {
                return 50;
            }

            @Override
            public int getFoodLoad() {
                return 0;
            }

            @Override
            public int getActionPoints() {
                return 5;
            }

            @Override
            public boolean isDead() {
                return false;
            }
        };

        memory.addAnt(ant);
//        memory.setQueenSpawn(location1);
    }

    @Test
    public void addVisibleLocationsTest() {
        List<ILocationInfo> locations = new ArrayList();
        ILocationInfo location = new ILocationInfo() {
            @Override
            public int getX() {
                return 1;
            }

            @Override
            public int getY() {
                return 0;
            }

            @Override
            public IAntInfo getAnt() {
                return null;
            }

            @Override
            public boolean isFilled() {
                return false;
            }

            @Override
            public boolean isRock() {
                return false;
            }

            @Override
            public int getFoodCount() {
                return 2;
            }
        };
        locations.add(location);

        memory.addVisibleLocations(locations);

        Map<Position, Tile> map = memory.getTiles();
        Position pos = new Position(1, 0);

        assertTrue(map.containsKey(pos));
        assertEquals(location.getFoodCount(), map.get(pos).getFoodCount());
    }

    @Test
    public void addAntTest() {
        ILocationInfo location = new ILocationInfo() {
            @Override
            public int getX() {
                return 1;
            }

            @Override
            public int getY() {
                return 1;
            }

            @Override
            public IAntInfo getAnt() {
                return null;
            }

            @Override
            public boolean isFilled() {
                return false;
            }

            @Override
            public boolean isRock() {
                return false;
            }

            @Override
            public int getFoodCount() {
                return 2;
            }
        };
        IAntInfo ant = new IAntInfo() {
            @Override
            public int antID() {
                return 2;
            }

            @Override
            public ITeamInfo getTeamInfo() {
                ITeamInfo team = new ITeamInfo() {
                    @Override
                    public int getTeamID() {
                        return 1;
                    }

                    @Override
                    public String getTeamName() {
                        return "Dream Team";
                    }
                };
                return team;
            }

            @Override
            public EAntType getAntType() {
                return EAntType.SCOUT;
            }

            @Override
            public ILocationInfo getLocation() {
                return location;
            }

            @Override
            public int getDirection() {
                return 1;
            }

            @Override
            public boolean carriesSoil() {
                return false;
            }

            @Override
            public int getAge() {
                return 2;
            }

            @Override
            public int getHitPoints() {
                return 50;
            }

            @Override
            public int getFoodLoad() {
                return 0;
            }

            @Override
            public int getActionPoints() {
                return 5;
            }

            @Override
            public boolean isDead() {
                return false;
            }
        };

        memory.addAnt(ant);

        List<IAntInfo> ants = memory.getAnts();

        assertEquals(2, ants.size());
        assertEquals(2, ants.get(1).antID());

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeAntTest() {
        ILocationInfo location = new ILocationInfo() {
            @Override
            public int getX() {
                return 1;
            }

            @Override
            public int getY() {
                return 1;
            }

            @Override
            public IAntInfo getAnt() {
                return null;
            }

            @Override
            public boolean isFilled() {
                return false;
            }

            @Override
            public boolean isRock() {
                return false;
            }

            @Override
            public int getFoodCount() {
                return 2;
            }
        };
        IAntInfo ant = new IAntInfo() {
            @Override
            public int antID() {
                return 2;
            }

            @Override
            public ITeamInfo getTeamInfo() {
                ITeamInfo team = new ITeamInfo() {
                    @Override
                    public int getTeamID() {
                        return 1;
                    }

                    @Override
                    public String getTeamName() {
                        return "Dream Team";
                    }
                };
                return team;
            }

            @Override
            public EAntType getAntType() {
                return EAntType.SCOUT;
            }

            @Override
            public ILocationInfo getLocation() {
                return location;
            }

            @Override
            public int getDirection() {
                return 1;
            }

            @Override
            public boolean carriesSoil() {
                return false;
            }

            @Override
            public int getAge() {
                return 2;
            }

            @Override
            public int getHitPoints() {
                return 50;
            }

            @Override
            public int getFoodLoad() {
                return 0;
            }

            @Override
            public int getActionPoints() {
                return 5;
            }

            @Override
            public boolean isDead() {
                return false;
            }
        };

        memory.removeAnt(ant);

        List<IAntInfo> ants = memory.getAnts();

        assertEquals(1, ants.size());
        assertEquals(2, ants.get(1).antID());
    }

    @Test
    public void setQueenSpawnTest() {
        ILocationInfo location = new ILocationInfo() {
            @Override
            public int getX() {
                return 0;
            }

            @Override
            public int getY() {
                return 0;
            }

            @Override
            public IAntInfo getAnt() {
                return null;
            }

            @Override
            public boolean isFilled() {
                return false;
            }

            @Override
            public boolean isRock() {
                return false;
            }

            @Override
            public int getFoodCount() {
                return 2;
            }
        };

        memory.setQueenSpawn(location);

        assertEquals(location.getX(), memory.getQueenSpawn().getX());
        assertEquals(location.getY(), memory.getQueenSpawn().getY());
    }
}
