package service;


import neural_network.models.*;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;



@Service
@Transactional
public class MyServiceClass {

    @Autowired
    SessionFactory session;
    public void save(GoogleAdsFlag googleAdsFlag){
        session.getCurrentSession().save(googleAdsFlag);
    }
    public GoogleAdsFlag updateGoogleAdsFlag(int id,String status){
        SQLQuery sqlQuery = session.getCurrentSession().createSQLQuery("update GoogleAdsFlag t set status='"+status+"' where t.id="+id);
        sqlQuery.executeUpdate();
        Criteria criteria = session.getCurrentSession().createCriteria(GoogleAdsFlag.class);
        criteria.add(Restrictions.eq("id",id));
        return (GoogleAdsFlag) criteria.uniqueResult();
    }

    public GoogleAdsFlag googleAdsFlag(){
        Criteria criteria = session.getCurrentSession().createCriteria(GoogleAdsFlag.class);
        return (GoogleAdsFlag) criteria.uniqueResult();
    }

    public void save(FullFaceFeatures fullFaceFeatures){
        session.getCurrentSession().save(fullFaceFeatures);
    }

    public boolean deleteFace(String deviceId, long tmpFullFaceFeatures_id) {

        String deleteFaceFeaturesQuery = "DELETE FROM FaceFeatures WHERE fullFaceFeatures_id = (SELECT FullFaceFeatures_id FROM FullFaceFeatures WHERE FullFaceFeatures_id = :FullFaceFeatures_id)";
        String deleteFullFaceFeaturesQuery = "DELETE FROM FullFaceFeatures WHERE deviceId = :deviceId AND FullFaceFeatures_id = :FullFaceFeatures_id";

        Query deleteFaceFeatures = session.getCurrentSession().createNativeQuery(deleteFaceFeaturesQuery);
        deleteFaceFeatures.setParameter("FullFaceFeatures_id", tmpFullFaceFeatures_id);
        int deletedFaceFeatures = deleteFaceFeatures.executeUpdate();

        Query deleteFullFaceFeatures = session.getCurrentSession().createNativeQuery(deleteFullFaceFeaturesQuery);
        deleteFullFaceFeatures.setParameter("deviceId", deviceId);
        deleteFullFaceFeatures.setParameter("FullFaceFeatures_id", tmpFullFaceFeatures_id);
        int deletedFullFaceFeatures = deleteFullFaceFeatures.executeUpdate();

        //return deletedFaceFeatures > 0 && deletedFullFaceFeatures > 0;
        return true;
    }





    public List<FullFaceFeatures> getFullFaceFeatures(){
        String sql = "SELECT * FROM  fullfacefeatures order by FullFaceFeatures_id desc limit 300";
        SQLQuery sqlQuery = session.getCurrentSession().createSQLQuery(sql).addEntity(FullFaceFeatures.class);
        List<FullFaceFeatures> list = sqlQuery.list();
        return list;
    }
    public FullFaceFeatures getFullFaceFeatures(long fullFaceFeatures_id){
        String sql = "SELECT * FROM  fullfacefeatures where FullFaceFeatures_id = "+fullFaceFeatures_id;
        SQLQuery sqlQuery = session.getCurrentSession().createSQLQuery(sql).addEntity(FullFaceFeatures.class);
        FullFaceFeatures fullFaceFeatures =(FullFaceFeatures) sqlQuery.uniqueResult();
        return fullFaceFeatures;
    }


    public List<ResponseModelImg> getFullFeatures(int city_id){

        String sql = "select f.fullFaceFeatures_id," +
                    " f.faceLabel," +
                    " f.identifier," +
                    " f.inp_date," +
                    " f.photoName," +
                    " f2.facefeatures_id," +
                    " f2.lat," +
                    " f2.lng  " +
                    " from FullFaceFeatures f " +
                    "inner join FaceFeatures f2 " +
                    "on f2.fullfacefeatures_id = f.fullFaceFeatures_id " +
                    "where city_id = "+city_id +" "+
                    "order by f.fullFaceFeatures_id desc LIMIT 300";
        SQLQuery sqlQuery = session.getCurrentSession().createSQLQuery(sql).addEntity(ResponseModelImg.class);
        List<ResponseModelImg> list = sqlQuery.list();
        return list;
    }

    public List<ResponseModelImg> getFullFeatures4imgs(){
        String sql = "select f.fullFaceFeatures_id," +
                " f.faceLabel," +
                " f.identifier," +
                " f.inp_date," +
                " f.photoName," +
                " f2.facefeatures_id," +
                " f2.lat," +
                " f2.lng " +
                " from FullFaceFeatures f " +
                "inner join FaceFeatures f2 " +
                "on f2.fullfacefeatures_id = f.fullFaceFeatures_id " +
                "order by f.fullFaceFeatures_id desc LIMIT 300";
        SQLQuery sqlQuery = session.getCurrentSession().createSQLQuery(sql).addEntity(ResponseModelImg.class);
        List<ResponseModelImg> list = sqlQuery.list();
        return list;
    }




    public void deleteAdsStatus(){
        SQLQuery sqlQuery = session.getCurrentSession().createSQLQuery("delete from GoogleAdsFlag");
        sqlQuery.executeUpdate();
    }


}
