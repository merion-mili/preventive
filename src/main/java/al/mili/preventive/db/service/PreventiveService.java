package al.mili.preventive.db.service;

import java.util.List;

import al.mili.preventive.db.model.Preventive;

public interface PreventiveService {

	public abstract void saveAllPreventives(List<Preventive> matches);

	public abstract void updateAllPreventives(List<Preventive> matches);

	public abstract List<Preventive> getPreventives();

}