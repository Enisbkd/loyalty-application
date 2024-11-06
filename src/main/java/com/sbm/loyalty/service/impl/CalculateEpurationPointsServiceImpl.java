//package com.sbm.loyalty.service.impl;
//
//import com.sbm.loyalty.service.CalculateEpurationPointsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//
//@Service
//public class CalculateEpurationPointsServiceImpl implements CalculateEpurationPointsService {
//
//    @Autowired
//    @Qualifier("topicsJdbcTemplate")
//    JdbcTemplate jdbcTemplate;
//
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//    @Override
//    public Integer findEntitiesByStatus() {
//        String sql = "SELECT * FROM MARKETING.PLAYCPTSLDHIS";
//        jdbcTemplate.execute(sql);
//        return 5;
//    }
//
//    String firstDayOfYear = LocalDate.of(LocalDate.now().getYear(), 1, 1).format(formatter);
//    String lastDayOfYear = LocalDate.of(LocalDate.now().getYear(), 12, 31).format(formatter);
//
//
////
////    public Integer calculate_points(int nucompte) {
////        return ((calculate_spe_ptret(nucompte) + calculate_spe_ptsie(nucompte)) - calculate_spe_ptdep(nucompte)) - calculate_SPE_PTSOLDE(nucompte);
////    }
////
////    public Integer calculate_spe_ptret(int nucompte) {
////        return calculate_spe_mtret_solde_dhis(nucompte) * calculate_spe_txeuropt();
////    }
//
////
//    public Integer calculate_spe_mtret_solde_dhis(int nucompte) {
//        Query query = entityManagerMarketingDB.createNativeQuery("select " +
//            "nvl(sum(a.MTSOLDERET), 0) SPE_MTRET " +
//            "from " +
//            "MARKETING.PLAYCPTSLDHIS a " +
//            "where " +
//            "NUCOMPTE = #NUCOMPTE " +
//            "and DACOMPTA = " + firstDayOfYear);
//        query.setParameter("NUCOMPTE", nucompte);
//        return query.getFirstResult();
//    }
//
////
////    public Integer calculate_spe_ptsie(int nucompte) {
////        return calculate_mtsie(nucompte) + calculate_mt777(nucompte);
////    }
////
////    public Integer calculate_mtsie(int nucompte) {
////        return calculate_spe_mtret_solde_sie(nucompte) * calculate_spe_txeuropt();
////    }
////
////
////    public Integer calculate_spe_mtret_solde_sie(int nucompte) {
////        Query query = entityManagerMarketingDB.createNativeQuery("select  " +
////            "sum(nvl(MTRET,0)) SPE_MTRET  " +
////            "from  " +
////            "MARKETING.PLAYCPTRLVRET a  " +
////            "where  " +
////            "DACOMPTA >= " + firstDayOfYear +
////            "and a.DACOMPTA < " + lastDayOfYear +
////            "and a.NUCOMPTE = :NUCOMPTE  " +
////            "and CODE = 'SIE'");
////        query.setParameter("NUCOMPTE", nucompte);
////        return query.getFirstResult();
////    }
////
////    public Integer calculate_mt777(int nucompte) {
////        return calculate_spe_mtret_solde_777(nucompte * calculate_spe_txeuropt());
////    }
////
////    public Integer calculate_spe_mtret_solde_777(int nucompte) {
////        Query query = entityManagerMarketingDB.createNativeQuery("select  " +
////            "sum(nvl(MTRET,0)) SPE_MTRET  " +
////            "from  " +
////            "MARKETING.PLAYCPTRLVRET a  " +
////            "where  " +
////            "DACOMPTA >=  " + firstDayOfYear +
////            "and a.DACOMPTA <  " + lastDayOfYear +
////            "and a.NUCOMPTE = :NUCOMPTE  " +
////            "and CODE = '777'");
////        query.setParameter("NUCOMPTE", nucompte);
////        return query.getFirstResult();
////    }
////
////    public Integer calculate_spe_ptdep(int nucompte) {
////        return -(calculate_spe_ptret_solde(nucompte));
////    }
////
////    public Integer calculate_spe_ptret_solde(int nucompte) {
////        Query query = entityManagerMarketingDB.createNativeQuery("SELECT sum(ptret) sptret  " +
////            "FROM  " +
////            "PLAYCPTRLVRET a   " +
////            "WHERE (  " +
////            " CODE = 'FAC'   " +
////            " or CODE = 'TMG'   " +
////            " or CODE = 'MIC'   " +
////            " or CODE = 'NYM'   " +
////            " or CODE = 'COU'   " +
////            " or code = 'TMS'   " +
////            " or code = 'CLO'   " +
////            "and nucompte = :NUCOMPTE   " +
////            "and dacompta >=  " + firstDayOfYear +
////            "and dacompta <= " + lastDayOfYear);
////        query.setParameter("NUCOMPTE", nucompte);
////
////        return query.getFirstResult();
////    }
////
////    public Integer calculate_SPE_PTSOLDE(int nucode) {
////        return -(calculate_spe_mtret_solde_epu(nucode) * calculate_spe_txeuropt());
////    }
////
////    public Integer calculate_spe_mtret_solde_epu(int nucode) {
////        Query query = entityManagerMarketingDB.createNativeQuery("select sum(nvl(MTRET,0)) SPE_MTRET FROM MARKETING.PLAYCPTRLVRET a   " +
////            "WHERE trunc(DACRE) >=  " + firstDayOfYear +
////            "AND trunc(a.DACRE) <  " + lastDayOfYear +
////            "AND a.NUCOMPTE = :NUCOMPTE   " +
////            "AND CODE = 'EPU'");
////        query.setParameter("NUCOMPTE", nucode);
////        return query.getFirstResult();
////    }
////
////
////    public Integer calculate_spe_txeuropt() {
////        Query query = entityManager2GammaDB.createNativeQuery("SELECT LIPARAM as SPE_LIPARAM from ENVPARAM WHERE COTYPARAM = 'TRANSFEP'  AND COPARAM = 'SLDRET'");
////        return query.getFirstResult();
////    }
//}
