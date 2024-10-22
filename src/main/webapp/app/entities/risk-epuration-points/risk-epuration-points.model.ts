export interface IRiskEpurationPoints {
  id: number;
  points?: number | null;
  validUntil?: number | null;
}

export type NewRiskEpurationPoints = Omit<IRiskEpurationPoints, 'id'> & { id: null };
