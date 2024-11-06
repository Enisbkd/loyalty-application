package com.sbm.loyalty.service;


import com.sbm.loyalty.domain.Response;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class CalculateEpurationPointsService {

    private static final Logger log = LoggerFactory.getLogger(CalculateEpurationPointsService.class);


    LocalDate firstDayOfYear = LocalDate.now().withDayOfYear(1);
    LocalDate lastDayOfYear = LocalDate.now().withDayOfYear(366);

    private final EntityManager emMarketing;
    private final EntityManager em2Gamma;

    public CalculateEpurationPointsService(@Qualifier("topicsEntityManagerFactory") EntityManagerFactory entityManagerMarketingDBFactory,
                                           @Qualifier("todosEntityManagerFactory") EntityManagerFactory entityManagerFactoryTwo, EntityManager emMarketing, EntityManager em2Gamma) {
        this.emMarketing = entityManagerMarketingDBFactory.createEntityManager();
        this.em2Gamma = entityManagerFactoryTwo.createEntityManager();
    }

    public String constructMessage(LocalDate validUntil) {
        if (validUntil.getMonthValue() == 12 && validUntil.getDayOfMonth() == 31) {
            return "DEFAULT";
        } else return "CUSTOM";
    }

    public Response constructResponse(int webid) {
        LocalDate validUntil = getValidUntil();
        return new Response(constructMessage(validUntil), points_calculator(webid), validUntil);
    }

    public Integer points_calculator(int webid) {
        Integer nuCli = calculate_nucli_from_webid(webid);
        if (nuCli == null) {
            return 0;
        }
        Integer spe_txeuropt = calculate_spe_txeuropt();
        Integer spe_mtret_solde = calculate_spe_ptret_solde(nuCli);
        int spe_ptsolde = -(spe_txeuropt * spe_mtret_solde);
        int spe_ptdep = -calculate_spe_ptret_solde(nuCli);
        Integer spe_mtret_solde_777 = calculate_spe_mtret_solde_777(nuCli);
        Integer mt777 = spe_mtret_solde_777 * spe_txeuropt;
        Integer spe_mtret_solde_sie = calculate_spe_mtret_solde_sie(nuCli);
        Integer mtSIE = spe_mtret_solde_sie * spe_txeuropt;
        Integer spe_ptsie = mtSIE + mt777;
        Integer spe_mtret_solde_dhis = calculate_spe_mtret_solde_dhis(nuCli);
        Integer spe_ptret = spe_mtret_solde_dhis * spe_txeuropt;
        int points = spe_ptret + spe_ptsie - spe_ptdep - spe_ptsolde;
        return Math.max(points, 0);
    }

    public Integer calculate_spe_ptret(int nucompte) {
        return calculate_spe_mtret_solde_dhis(nucompte) * calculate_spe_txeuropt();
    }


    public Integer calculate_spe_mtret_solde_dhis(int nucompte) {

        Query query = emMarketing.createNativeQuery("select " +
            "nvl(sum(a.MTSOLDERET), 0) SPE_MTRET " +
            "from " +
            "MARKETING.PLAYCPTSLDHIS a " +
            "where " +
            "NUCOMPTE = :NUCOMPTE " +
            " and DACOMPTA = :FIRSTDAY");
        query.setParameter("NUCOMPTE", nucompte);
        query.setParameter("FIRSTDAY", firstDayOfYear);
        Integer result = resultChecker(query.getSingleResult());
        log.debug("Result of spe_mtret_solde_dhis : " + result);
        return 0;
    }


    public Integer calculate_spe_ptsie(int nucompte) {
        return calculate_mtsie(nucompte) + calculate_mt777(nucompte);
    }

    public Integer calculate_mtsie(int nucompte) {
        return calculate_spe_mtret_solde_sie(nucompte) * calculate_spe_txeuropt();
    }


    public Integer calculate_spe_mtret_solde_sie(int nucompte) {
        Query query = emMarketing.createNativeQuery("select  " +
            "sum(nvl(MTRET,0)) SPE_MTRET  " +
            "from  " +
            "MARKETING.PLAYCPTRLVRET a  " +
            "where  " +
            "DACOMPTA >= :FIRSTDAY " +
            "and a.DACOMPTA < :LASTDAY " +
            "and a.NUCOMPTE = :NUCOMPTE  " +
            "and CODE = 'SIE'");
        query.setParameter("NUCOMPTE", nucompte);
        query.setParameter("FIRSTDAY", firstDayOfYear);
        query.setParameter("LASTDAY", lastDayOfYear);

        Integer result = resultChecker(query.getSingleResult());
        log.debug("Result of spe_mtret_solde_sie : " + result);
        return 0;
    }

    public Integer calculate_mt777(int nucompte) {
        return calculate_spe_mtret_solde_777(nucompte * calculate_spe_txeuropt());
    }

    public Integer calculate_spe_mtret_solde_777(int nucompte) {
        Query query = emMarketing.createNativeQuery("select  " +
            "sum(nvl(MTRET,0)) SPE_MTRET  " +
            "from  " +
            "MARKETING.PLAYCPTRLVRET a  " +
            "where  " +
            "DACOMPTA >=  :FIRSTDAY " +
            "and a.DACOMPTA <  :LASTDAY " +
            "and a.NUCOMPTE = :NUCOMPTE  " +
            "and CODE = '777'");
        query.setParameter("NUCOMPTE", nucompte);
        query.setParameter("LASTDAY", lastDayOfYear);
        query.setParameter("FIRSTDAY", firstDayOfYear);


        Integer result = resultChecker(query.getSingleResult());
        log.debug("Result of spe_mtret_solde_777 : " + result);
        return result;
    }

    public Integer calculate_spe_ptdep(int nucompte) {
        return -(calculate_spe_ptret_solde(nucompte));
    }

    public Integer calculate_spe_ptret_solde(int nucompte) {
        Query query = emMarketing.createNativeQuery("SELECT sum(ptret) sptret  " +
            "FROM  " +
            "MARKETING.PLAYCPTRLVRET a   " +
            "WHERE (  " +
            " CODE = 'FAC'   " +
            " or CODE = 'TMG'   " +
            " or CODE = 'MIC'   " +
            " or CODE = 'NYM'   " +
            " or CODE = 'COU'   " +
            " or code = 'TMS'   " +
            " or code = 'CLO'   " +
            "and nucompte = :NUCOMPTE   " +
            "and dacompta >=  :FIRSTDAY " +
            "and dacompta <= :LASTDAY)");
        query.setParameter("NUCOMPTE", nucompte);
        query.setParameter("FIRSTDAY", firstDayOfYear);
        query.setParameter("LASTDAY", lastDayOfYear);

        Integer result = resultChecker(query.getSingleResult());
        log.debug("Result of spe_ptret_solde : " + result);
        return result;
    }

    public Integer calculate_SPE_PTSOLDE(int nucode) {
        return -(calculate_spe_mtret_solde_epu(nucode) * calculate_spe_txeuropt());
    }

    public Integer calculate_spe_mtret_solde_epu(int nucode) {
        Query query = emMarketing.createNativeQuery("select sum(nvl(MTRET,0)) SPE_MTRET FROM MARKETING.PLAYCPTRLVRET a   " +
            "WHERE trunc(DACRE) >=  :FIRSTDAY " +
            "AND trunc(a.DACRE) <  :LASTDAY " +
            "AND a.NUCOMPTE = :NUCOMPTE   " +
            "AND CODE = 'EPU'");
        query.setParameter("NUCOMPTE", nucode);
        query.setParameter("FIRSTDAY", firstDayOfYear);
        query.setParameter("LASTDAY", lastDayOfYear);
        Integer result = resultChecker(query.getSingleResult());
        log.debug("Result of spe_mtret_solde_epu : " + result);
        return result;
    }

    public Integer calculate_nucli_from_webid(int webid) {
        String queryStr = "select nucli from MARKETING.WEBCLI026 where WEBID=:WEBID AND FLACTIF='A'";
        Query query = emMarketing.createNativeQuery(queryStr);
        query.setParameter("WEBID", String.valueOf(webid));
        try {
            return resultChecker(query.getSingleResult());
        } catch (Exception e) {
            return null;
        }
    }


    public Integer calculate_spe_txeuropt() {
        Query query = em2Gamma.createNativeQuery("SELECT LIPARAM as SPE_LIPARAM from DGDINFRA.ENVPARAM WHERE COTYPARAM = 'TRANSFEP'  AND COPARAM = 'SLDRET'");
        Integer result = resultChecker(query.getSingleResult());
        log.debug("Result of spe_txeuropt : " + result);
        return result;
    }

    public LocalDate getValidUntil() {
        Query query = em2Gamma.createNativeQuery("SELECT LIPARAM as SPE_LIPARAM from DGDINFRA.ENVPARAM WHERE COTYPARAM = 'FIDEXP'  AND COPARAM = 'FID'");
        String result = query.getSingleResult().toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate validUntil = LocalDate.parse(result, formatter);
        log.debug("validUntil : " + validUntil);
        return validUntil;
    }

    public Integer resultChecker(Object result) {
        return Optional.ofNullable(result)
            .map(Object::toString)
            .map(str -> {
                try {
                    return Integer.valueOf(str);
                } catch (NumberFormatException e) {
                    return 0;
                }
            })
            .orElse(0);
    }
}
