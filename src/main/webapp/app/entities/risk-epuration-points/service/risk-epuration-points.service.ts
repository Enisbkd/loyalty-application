import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRiskEpurationPoints, NewRiskEpurationPoints } from '../risk-epuration-points.model';

export type PartialUpdateRiskEpurationPoints = Partial<IRiskEpurationPoints> & Pick<IRiskEpurationPoints, 'id'>;

export type EntityResponseType = HttpResponse<IRiskEpurationPoints>;
export type EntityArrayResponseType = HttpResponse<IRiskEpurationPoints[]>;

@Injectable({ providedIn: 'root' })
export class RiskEpurationPointsService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/risk-epuration-points');

  create(riskEpurationPoints: NewRiskEpurationPoints): Observable<EntityResponseType> {
    return this.http.post<IRiskEpurationPoints>(this.resourceUrl, riskEpurationPoints, { observe: 'response' });
  }

  update(riskEpurationPoints: IRiskEpurationPoints): Observable<EntityResponseType> {
    return this.http.put<IRiskEpurationPoints>(
      `${this.resourceUrl}/${this.getRiskEpurationPointsIdentifier(riskEpurationPoints)}`,
      riskEpurationPoints,
      { observe: 'response' },
    );
  }

  partialUpdate(riskEpurationPoints: PartialUpdateRiskEpurationPoints): Observable<EntityResponseType> {
    return this.http.patch<IRiskEpurationPoints>(
      `${this.resourceUrl}/${this.getRiskEpurationPointsIdentifier(riskEpurationPoints)}`,
      riskEpurationPoints,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRiskEpurationPoints>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRiskEpurationPoints[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRiskEpurationPointsIdentifier(riskEpurationPoints: Pick<IRiskEpurationPoints, 'id'>): number {
    return riskEpurationPoints.id;
  }

  compareRiskEpurationPoints(o1: Pick<IRiskEpurationPoints, 'id'> | null, o2: Pick<IRiskEpurationPoints, 'id'> | null): boolean {
    return o1 && o2 ? this.getRiskEpurationPointsIdentifier(o1) === this.getRiskEpurationPointsIdentifier(o2) : o1 === o2;
  }

  addRiskEpurationPointsToCollectionIfMissing<Type extends Pick<IRiskEpurationPoints, 'id'>>(
    riskEpurationPointsCollection: Type[],
    ...riskEpurationPointsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const riskEpurationPoints: Type[] = riskEpurationPointsToCheck.filter(isPresent);
    if (riskEpurationPoints.length > 0) {
      const riskEpurationPointsCollectionIdentifiers = riskEpurationPointsCollection.map(riskEpurationPointsItem =>
        this.getRiskEpurationPointsIdentifier(riskEpurationPointsItem),
      );
      const riskEpurationPointsToAdd = riskEpurationPoints.filter(riskEpurationPointsItem => {
        const riskEpurationPointsIdentifier = this.getRiskEpurationPointsIdentifier(riskEpurationPointsItem);
        if (riskEpurationPointsCollectionIdentifiers.includes(riskEpurationPointsIdentifier)) {
          return false;
        }
        riskEpurationPointsCollectionIdentifiers.push(riskEpurationPointsIdentifier);
        return true;
      });
      return [...riskEpurationPointsToAdd, ...riskEpurationPointsCollection];
    }
    return riskEpurationPointsCollection;
  }
}
