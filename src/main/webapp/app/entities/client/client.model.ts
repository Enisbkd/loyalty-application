export interface IClient {
  id: number;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  dateOfBirth?: string | null;
  emailVerified?: boolean | null;
  signUpDate?: string | null;
}

export type NewClient = Omit<IClient, 'id'> & { id: null };
