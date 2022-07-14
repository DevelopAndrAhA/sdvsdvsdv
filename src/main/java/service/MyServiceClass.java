package service;


import neural_network.models.*;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public void delete (FaceFeatures faceFeatures,FullFaceFeatures fullFaceFeatures){
        session.getCurrentSession().delete(faceFeatures);
        delete(fullFaceFeatures);
    }
    public void delete (FullFaceFeatures fullFaceFeatures){
        session.getCurrentSession().delete(fullFaceFeatures);
    }

    public List<FullFaceFeatures> getFullFaceFeatures(){
        Criteria criteria = session.getCurrentSession().createCriteria(FullFaceFeatures.class);
        List<FullFaceFeatures> list = criteria.list();
        return list;
    }

    public List<ResponseModel> getFullFeatures(String inp_date){
        String sql = "select f.fullFaceFeatures_id, f.faceLabel, f.identifier, f.inp_date, f.photoName, f2.facefeatures_id, f2.lat, f2.lng ,f2.features " +
                "from FullFaceFeatures f inner join FaceFeatures f2 on f2.fullfacefeatures_id = f.fullFaceFeatures_id " +
                "where to_char(f.inp_date,'yyyy-mm-dd')='"+inp_date+"'  limit 300";
        SQLQuery sqlQuery = session.getCurrentSession().createSQLQuery(sql).addEntity(ResponseModel.class);
        List<ResponseModel> list = sqlQuery.list();
        return list;
    }

    public List<ResponseModelImg> getFullFeatures(double lat,double lng){
        double lat_temp = lat+0.001000;
        double lng_temp = lng+0.001000;

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
                    "where (f2.lat between "+lat+" and  " +lat_temp + " or (f2.lng between "+lng+" and "+lng_temp+")) "+
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
