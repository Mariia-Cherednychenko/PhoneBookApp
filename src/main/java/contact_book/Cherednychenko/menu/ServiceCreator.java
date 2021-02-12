/*
package contact_book.Cherednychenko.menu;

import contact_book.Cherednychenko.annotations.CreateIfMode;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


public class ServiceCreator {
    Map<Class,Object> instances = new HashMap<>();
    private final String MODE;
    private Map<String, String> stringParams;
    private List<Object> params;

    public ServiceCreator(String MODE, Map<String, String> stringParams, List<Object> params) {
        this.MODE = MODE;
        this.stringParams=stringParams;
        this.params=params;
    }

    private List<Class> find (String pacageName){//contact_book.Cherednychenko.dto
        String baePath = pacageName.replace("\\.","/");
        URL res = getClass().getClassLoader().getResource(baePath);
        try {
            File dir = new File(res.toURI());
            Arrays.stream(dir.listFiles())
                    .map(f->f.getName())
                    .filter(f-> f.endsWith(".class"))
                    .map(name-> pacageName+ "."+name)
                    .map(name-> name.substring(0,name.length()-6))
                    .map(className-> {
                        try {
                            return Class.forName(className);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }).collect(Collectors.toList())
                    .forEach(n-> System.out.println(n));

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> T getService(Class <T> serviceClass){
        if (instances.containsKey(serviceClass)){
            return (T) instances.get(serviceClass);
        }

        List<Class> classes = find("contact_book.Cherednychenko.dto.services.implementation").stream()
                .filter(c->serviceClass.isAssignableFrom(c))
                .filter(c->c.isAnnotationPresent(CreateIfMode.class))
                .collect(Collectors.toList());
        for (Class aClass : classes) {
            //System.out.println(aClass);
            CreateIfMode modeAnnotation = (CreateIfMode) aClass.getAnnotation(CreateIfMode.class);
            String [] modes = modeAnnotation.value();
            if (Arrays.asList(modes).contains(MODE)){
                Constructor constructor = aClass.getConstructors()[0];

                List<Object> construcotArgs = Arrays.stream(constructor.getParameters())
                        .map (p->{
                            if(p.getType() == String.class)
                                return stringParams.get(p.getName());
                            for (param.getClass() == p.getType()) {

                            }
                        })



                System.out.println(constructor);
                try {
                    return (T) constructor.newInstance(construcotArgs.toArray());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }


                System.out.println(aClass);
            }
        }
      return null;

    }
}
*/
