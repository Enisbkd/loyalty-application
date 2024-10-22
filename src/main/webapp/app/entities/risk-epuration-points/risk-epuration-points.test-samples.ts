import { IRiskEpurationPoints, NewRiskEpurationPoints } from './risk-epuration-points.model';

export const sampleWithRequiredData: IRiskEpurationPoints = {
  id: 10601,
};

export const sampleWithPartialData: IRiskEpurationPoints = {
  id: 6473,
  validUntil: 18626,
};

export const sampleWithFullData: IRiskEpurationPoints = {
  id: 12524,
  points: 20814,
  validUntil: 3287,
};

export const sampleWithNewData: NewRiskEpurationPoints = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
