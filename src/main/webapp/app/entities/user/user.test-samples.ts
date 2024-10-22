import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 17384,
  login: 'd^hUl@hgI',
};

export const sampleWithPartialData: IUser = {
  id: 24823,
  login: 'h.',
};

export const sampleWithFullData: IUser = {
  id: 32194,
  login: 'uy@3i8',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
