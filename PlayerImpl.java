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
		// Delete everything in this method and include your implementation
		// here.
		double highestAlpha = Double.NEGATIVE_INFINITY;
		double highestAlphaCompare = Double.NEGATIVE_INFINITY;
		double lowestBeta = Double.POSITIVE_INFINITY;
		double lowestBetaCompare = Double.POSITIVE_INFINITY;
		
		for (int i = 0;; i++) {
			int depth = i;
            System.out.println("depth: " + i);
			Position[] positions = getLegalMoves(board, color);
            System.out.println("all legal moves: " + positions.length);
			for (int j = 0; j < positions.length; j++) {
				GameState gs = makeMove(board, positions[j], color);
                visited(gs.getBoard());
                
				if (color.equals(Color.WHITE)) {
					//System.out.println("Enter white");
					
                    System.out.println("highest alpha: " + highestAlpha);
					if (highestAlpha < getScore(board)) {
                        highestAlphaCompare = MaxValue(gs, highestAlpha, lowestBeta, depth);
                        if(highestAlphaCompare > highestAlpha) {
                        	highestAlpha = highestAlphaCompare;
    						bestSoFar = positions[j];
                        }
						//System.out.println("Enter score updated white;");
					}
				} else if (color.equals(Color.BLACK)) {
					//System.out.println("Enter black");
                    System.out.println("lowestbeta: " + lowestBeta);
					//System.out.println("lowest beta is " + lowestBeta);
					if (lowestBeta > getScore(board)) {
						lowestBetaCompare = MinValue(gs, highestAlpha, lowestBeta, depth);  
                        if(lowestBetaCompare < lowestBeta) {
                        	lowestBeta = lowestBetaCompare;
    						bestSoFar = positions[j];
                        }
						//System.out.println("Enter score updated black;");
					}
				}
			}
		} 
	}

	public double MaxValue(GameState gs, double alpha, double beta, int depth) {
		if (gs.getMove() == Move.GAME_OVER || depth == 0) {
			return getScore(gs.getBoard());
		} else {
			Color c = null;
			if (gs.getMove() == Move.BLACK) {
				c = Color.BLACK;
			} else {
				c = Color.WHITE;
			}

			Position[] positions = getLegalMoves(gs.getBoard(), c);
			for (int i = 0; i < positions.length; i++) {
				GameState gamestate = makeMove(gs.getBoard(), positions[i], c);
                visited(gs.getBoard());
                //System.out.println("positions.length: " + positions.length + " positions: " + positions[i]);
				if (gs.getMove() != gamestate.getMove()) {
					alpha = Math.max(alpha,
							MinValue(gamestate, alpha, beta, depth--));
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
		if (gs.getMove() == Move.GAME_OVER || depth == 0) {
			return getScore(gs.getBoard());
		} else {
			Color c = null;
			if (gs.getMove() == Move.BLACK) {
				c = Color.BLACK;
			} else {
				c = Color.WHITE;
			}

			Position[] positions = getLegalMoves(gs.getBoard(), c);
			for (int i = 0; i < positions.length; i++) {
				GameState gamestate = makeMove(gs.getBoard(), positions[i], c);               
                visited(gs.getBoard());
                //System.out.println("positions.length: " + positions.length + " positions: " + positions[i]);

				if (gs.getMove() != gamestate.getMove()) {
					beta = Math.min(beta,
							MaxValue(gamestate, alpha, beta, depth--));
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
