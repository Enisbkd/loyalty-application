import { IRiskEpurationPoints } from 'app/entities/risk-epuration-points/risk-epuration-points.model';

export interface ILoyaltyPoints {
  id: number;
  statusPoints?: number | null;
  myPoints?: number | null;
  riskEpurationPoints?: IRiskEpurationPoints | null;
}

export type NewLoyaltyPoints = Omit<ILoyaltyPoints, 'id'> & { id: null };
