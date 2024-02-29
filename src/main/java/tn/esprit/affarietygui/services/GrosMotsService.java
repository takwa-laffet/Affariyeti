package tn.esprit.affarietygui.services;

import tn.esprit.affarietygui.models.GrosMots;

import java.sql.SQLException;
import java.util.List;

public class GrosMotsService implements IService<GrosMots> {
    @Override
    public int ajouter(GrosMots grosMots) throws SQLException {
        return 0;
    }

    @Override
    public void modifier(GrosMots grosMots) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    @Override
    public List<GrosMots> recuperer() throws SQLException {
        return null;
    }
}
