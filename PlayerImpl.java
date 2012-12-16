///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  PlayerImpl.java
// File:             PlayerImpl.java
// Semester:         CS540 Section 2 (Fall 2012)
// Program:			 Programming Assignment 5(Reversi AI)
//
// Author:           Kah Jing Lee(klee224@wisc.edu)
// CS Login:         kah
// Lecturer's Name:  Bryan R. Gibson
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.Random;

public class PlayerImpl extends Player {
	private Color color;
	private Position bestSoFar;

	@Override
	public void color(Color color) {
		this.color = color;
	}

	@Override
	public void move(Color[][] board) {

		for (int i = 0;; i++) {
			double highestAlpha = Double.NEGATIVE_INFINITY;
			double highestAlphaCompare = Double.NEGATIVE_INFINITY;
			double lowestBeta = Double.POSITIVE_INFINITY;
			double lowestBetaCompare = Double.POSITIVE_INFINITY;

			int depth = i;
			Position[] positions = getLegalMoves(board, color);
			for (int j = 0; j < positions.length; j++) {
				GameState gs = makeMove(board, positions[j], color);
				visited(gs.getBoard());

				if (color.toString().equals(Color.WHITE.toString())) {
					highestAlphaCompare = MaxValue(gs, highestAlpha,
							lowestBeta, depth);
					if (highestAlphaCompare > highestAlpha) {
						highestAlpha = highestAlphaCompare;
						bestSoFar = positions[j];
					}
				} else if (color.toString().equals(Color.BLACK.toString())) {
					lowestBetaCompare = MinValue(gs, highestAlpha, lowestBeta,
							depth);
					if (lowestBetaCompare < lowestBeta) {
						lowestBeta = lowestBetaCompare;
						bestSoFar = positions[j];
					}
				}
			}
		}
	}

	public double MaxValue(GameState gs, double alpha, double beta, int depth) {
		if (gs.getMove().toString().equals(Move.GAME_OVER.toString())
				|| depth == 0) {
			return getScore(gs.getBoard());
		} else {
			Color c = null;
			if (gs.getMove().toString().equals(Move.BLACK.toString())) {
				c = Color.BLACK;
			} else {
				c = Color.WHITE;
			}
			Position[] positions = getLegalMoves(gs.getBoard(), c);
			for (int i = 0; i < positions.length; i++) {
				GameState gamestate = makeMove(gs.getBoard(), positions[i], c);
				visited(gamestate.getBoard());
				if (!gamestate.getMove().toString()
						.equals(gs.getMove().toString())) {
					alpha = Math.max(alpha,
							MinValue(gamestate, alpha, beta, depth - 1));
				} else {
					alpha = Math.max(alpha,
							MaxValue(gamestate, alpha, beta, depth));
				}
			}
			if (alpha >= beta) {
				return beta;
			}
		}
		return alpha;
	}

	public double MinValue(GameState gs, double alpha, double beta, int depth) {
		if (gs.getMove().toString().equals(Move.GAME_OVER.toString())
				|| depth == 0) {
			return getScore(gs.getBoard());
		} else {
			Color c = null;
			if (gs.getMove().toString().equals(Move.BLACK.toString())) {
				c = Color.BLACK;
			} else {
				c = Color.WHITE;
			}

			Position[] positions = getLegalMoves(gs.getBoard(), c);
			for (int i = 0; i < positions.length; i++) {
				GameState gamestate = makeMove(gs.getBoard(), positions[i], c);
				visited(gamestate.getBoard());
				if (!gamestate.getMove().toString()
						.equals(gs.getMove().toString())) {
					beta = Math.min(beta,
							MaxValue(gamestate, alpha, beta, depth - 1));
				} else {
					beta = Math.min(beta,
							MinValue(gamestate, alpha, beta, depth));
				}
			}
			if (alpha >= beta) {
				return alpha;
			}
		}
		return beta;
	}

	@Override
	public Position bestSoFar() {
		return bestSoFar;
	}
}
