package com.nwm.coauthor.service.util;

import org.springframework.stereotype.Component;

@Component
public class SelectOptionsUtil {
//    private static final Integer UBER_GAME_SEASON_ID = 14;
//
//    @Autowired
//    private FantasyMetadataProvider fantasyMetadataProvider;
//
//    @Autowired
//    private SportsMetadataProvider sportsProvider;
//
//    public List<JsonSelect> getSports() {
//        List<JsonSelect> selections = new ArrayList<JsonSelect>();
//
//        List<Map<String, Object>> sports = (List<Map<String, Object>>) sportsProvider.getSports().get("sports");
//
//        for (Map<String, Object> sport : sports) {
//            String displayValue = (String) sport.get("name");
//            Sport value = new Sport();
//
//            value.setName(displayValue);
//            value.setId((Integer) sport.get("id"));
//            value.setLink((String) ((Map<String, Object>) ((Map<String, Object>) ((Map<String, Object>) sport.get("links")).get("api")).get("sports")).get("href"));
//
//            selections.add(JsonSelect.create(displayValue, value));
//        }
//
//        return selections;
//    }
//
//    public List<JsonSelect> getBooleanOptions() {
//        List<JsonSelect> selections = new ArrayList<JsonSelect>();
//
//        selections.add(JsonSelect.create("True", true));
//        selections.add(JsonSelect.create("False", false));
//
//        return selections;
//    }
//
//    public List<JsonSelect> getOrganizations() {
//        List<JsonSelect> selections = new ArrayList<JsonSelect>();
//
//        try {
//            Map<Integer, Organization> orgs = fantasyMetadataProvider.getAllFantasyOrganizations();
//
//            for (Entry<Integer, Organization> entry : orgs.entrySet()) {
//                selections.add(JsonSelect.create(entry.getValue().getName(), entry.getValue().getId()));
//            }
//        } catch (FuseBackendException e) {
//        }
//
//        return selections;
//    }
//
//    public List<JsonSelect> getUberSeasons() {
//        List<JsonSelect> selections = new ArrayList<JsonSelect>();
//
//        List<Season> seasons = null;
//        try {
//            seasons = new LinkedList<Season>(fantasyMetadataProvider.getAllFantasySeasonsByGameId(UBER_GAME_SEASON_ID).values());
//        } catch (FuseBackendException e) {
//        }
//
//        for (Season season : seasons) {
//            selections.add(JsonSelect.create(season.getName(), season.getId()));
//        }
//
//        return selections;
//    }
//
//    public List<JsonSelect> getGames() {
//        List<JsonSelect> selections = new ArrayList<JsonSelect>();
//
//        try {
//            Map<Integer, Game> games = fantasyMetadataProvider.getAllFantasyGames();
//
//            for (Entry<Integer, Game> entry : games.entrySet()) {
//                selections.add(JsonSelect.create(entry.getValue().getName(), entry.getValue().getId()));
//            }
//        } catch (FuseBackendException e) {
//        }
//
//        return selections;
//    }
}
