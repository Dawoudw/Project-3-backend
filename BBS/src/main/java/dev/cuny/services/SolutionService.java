package dev.cuny.services;

import java.util.List;

import dev.cuny.entities.*;

public interface SolutionService {
	//CREATE
	Solution createSolution(Solution s);
	//READ
	Solution getSolutionById(int id);
	List<Solution> getAllSolutions();
	List<Solution> getSolutionsByClient(Client c);
	List<Solution>getSolutionByBugReport(BugReport br);
	//UPDATE
	Solution updateSolution(Solution s);
	//DELETE
	boolean deleteSolution(Solution s);
}
