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
            //System.out.println("depth: " + i + " depth check: " + depth);
			Position[] positions = getLegalMoves(board, color);
            //System.out.println("all legal moves: " + positions.length);
			for (int j = 0; j < positions.length; j++) {
				GameState gs = makeMove(board, positions[j], color);
                visited(gs.getBoard());
                
				if (color.toString().equals(Color.WHITE.toString())) {
					//System.out.println("Enter white");
					
                    System.out.println("highest alpha: " + highestAlpha);
					//if (highestAlpha < getScore(board)) {
                    	System.out.println("depth max: " + depth);
                        highestAlphaCompare = MaxValue(gs, highestAlpha, lowestBeta, depth);
                        if(highestAlphaCompare > highestAlpha) {
                        	highestAlpha = highestAlphaCompare;
    						bestSoFar = positions[j];
                        }
						//System.out.println("Enter score updated white;");
					//}
				} else if (color.toString().equals(Color.BLACK.toString())) {
					//System.out.println("Enter black");
                    //System.out.println("lowestbeta: " + lowestBeta);
					//System.out.println("lowest beta is " + lowestBeta);
					//if (lowestBeta > getScore(board)) {
					System.out.println("depth min: " + depth);
						lowestBetaCompare = MinValue(gs, highestAlpha, lowestBeta, depth);  
                        if(lowestBetaCompare < lowestBeta) {
                        	lowestBeta = lowestBetaCompare;
    						bestSoFar = positions[j];
                        }
						//System.out.println("Enter score updated black;");
					//}
				}
			}
		} 
	}

	public double MaxValue(GameState gs, double alpha, double beta, int depth) {
		if (gs.getMove().toString().equals(Move.GAME_OVER.toString()) || depth == 0) {
			return getScore(gs.getBoard());
		} else {
			Color c = null;
			if (gs.getMove().toString().equals(Move.BLACK.toString())) {
				//System.out.println("max black");
				c = Color.BLACK;
			} else {
				//System.out.println("max white");
				c = Color.WHITE;
			}
			//System.out.println("max value called");

			//System.out.println("depth max: " + depth);
			Position[] positions = getLegalMoves(gs.getBoard(), c);
			for (int i = 0; i < positions.length; i++) {
				//System.out.println("depth value: " + depth);
				GameState gamestate = makeMove(gs.getBoard(), positions[i], c);
                visited(gamestate.getBoard());
                //System.out.println("positions.length: " + positions.length + " positions: " + positions[i]);
				if (!gamestate.getMove().toString().equals(gs.getMove().toString())) {
					alpha = Math.max(alpha,
							MinValue(gamestate, alpha, beta, depth-1));
				} else {
					alpha = Math.max(alpha,
							MaxValue(gamestate, alpha, beta, depth));
				}

				//System.out.println("max i: " + i);
			}
			if (alpha >= beta) {
				return beta;
			}
		}
		return alpha;
	}

	public double MinValue(GameState gs, double alpha, double beta, int depth) {
		if (gs.getMove().toString().equals(Move.GAME_OVER.toString()) || depth == 0) {
			return getScore(gs.getBoard());
		} else {
			Color c = null;
			if (gs.getMove().toString().equals(Move.BLACK.toString())) {
				c = Color.BLACK;
			} else {
				c = Color.WHITE;
			}
			//System.out.println("check here color: " + c);
			
			Position[] positions = getLegalMoves(gs.getBoard(), c);
			//System.out.println("positions.length: " + positions.length);
			for (int i = 0; i < positions.length; i++) {
				GameState gamestate = makeMove(gs.getBoard(), positions[i], c);               
                visited(gamestate.getBoard());
                //System.out.println("positions.length: " + positions.length + " positions: " + positions[i]);
              //  System.out.println("old gs state: " + gs.getMove() + " new gs state: " + gamestate.getMove());
				if (!gamestate.getMove().toString().equals(gs.getMove().toString())) {
					beta = Math.min(beta,
							MaxValue(gamestate, alpha, beta, depth-1));
					//System.out.println("here");
				} else {
					beta = Math.min(beta,
							MinValue(gamestate, alpha, beta, depth));
					//System.out.println("cant be here");
				}
				//System.out.println("min i: " + i);
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
