package antipadroes.views;
		

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;



import org.eclipse.ui.*;

import org.eclipse.swt.SWT;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
























import org.eclipse.swt.widgets.Button;

import org.eclipse.swt.widgets.Label;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;

import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GCData;

import org.eclipse.swt.graphics.RGB;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Model.EntityModel;





/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class SampleView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "umlmaster2.views.SampleView";
	ListViewer viewer;
    private URI arquivo_uml ;

	private IWorkspaceRoot root;
    private Color color;
    private Color color1 ;
    private Device device;
	private Composite parent ;
    private ArrayList<Label> lista = new ArrayList<>();
    private ArrayList<MiniCircle> circles = new ArrayList<>();
    private StyledText container;
    
    /*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	private StyleRange range;
	
	private Color cinza_escuro;
    private RGB rgb ;
	 
	//class ViewContentProvider implements IStructuredContentProvider {
		//public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		//}
		//public void dispose() {
		//}
		//public Object[] getElements(Object parent) {
		//ArrayList metric = new ArrayList<Metrics>();
		//Metrics CBO = new Metrics();
		//CBO.setMetrics("CBO 3");
		//metric.add(CBO);
		//return metric;
		//}
	//}
	




	
	public void init(IViewSite site) {
        try {
			super.init(site);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(final Composite parent1) {
	          
		      parent = parent1;
		     
		       
		      device = new Device() {
					
					@Override
					public long internal_new_GC(GCData arg0) {
						// TODO Auto-generated method stub
						return 0;
					}
					
					@Override
					public void internal_dispose_GC(long arg0, GCData arg1) {
						// TODO Auto-generated method stub
						
					}
				};
				
				cinza_escuro = new Color(device, 100,100,100); 
		      //final ScrolledComposite comp = new ScrolledComposite(parent, SWT.V_SCROLL|SWT.BORDER) ;
				 
				
		     // parent.setSize(1000, parent.getSize().y);
		     
		     
		     
		      
		     
		    
		      
		      
		     
        IWorkspace work = ResourcesPlugin.getWorkspace();
        IResourceChangeListener listener = new IResourceChangeListener() {
        	 

			

			private IResource resource;
			
			
			
			private Color cinza_escuro;
			private int parametros = 0;
			
			private ArrayList<EntityModel> list_of_Entitys = new ArrayList<>() ;
		

			public void resourceChanged(IResourceChangeEvent event) {
        	    
				root = ResourcesPlugin.getWorkspace().getRoot();
        		IResourceDelta[]  recurso  = event.getDelta().getAffectedChildren();
                   FontData fo = new FontData("Andale Mono", 10, SWT.NONE);
				
				 color = new Color(device, 80, 180, 80);
				 color1 = new Color(device, 230, 40, 40);
				 cinza_escuro = new Color(device, 100,100,100);
				 Color  prata = new Color(device, 220, 220, 220);
				 make_path(recurso);
				 list_of_Entitys.clear();
        		try {
        		File input = new File(arquivo_uml);
        		
        		Document doc = Jsoup.parse(input , "UTF-8");
				
				Elements classes = doc.select("packagedElement[xsi:type=\"uml:Class\"]");
				
				int methods = (doc.select("ownedOperation").size());
				int  countx = 100 ;
				int county = 20;
				float mediamethods = methods/(float)classes.size();
				if (methods >=1){
				parametros  = doc.select("ownedParameter").size()/methods;
				EntityModel.media = parametros ;
				}
				for(Label dispensado :lista){
					int numero =lista.indexOf(dispensado);
					dispensado.dispose();
					circles.get(numero).dispose();
				}
				
				for (Element classe  : classes) {
					EntityModel cla = new EntityModel(classe); 
					list_of_Entitys.add(cla);
				   
					Label label = new Label(parent, SWT.NONE);
					 label.setText( cla.getName());
				      label.setForeground(prata);
				      label.setFont(new Font(device, fo));
				      label.setSize((label.getText().length())*8 , 20);
				      label.setLocation(0, county);
				     
				      lista.add(label);
				      MiniCircle frescura = new MiniCircle(parent, SWT.NONE);
					    if(cla.numerodeMetodos()<= mediamethods) {
					    	rgb = new RGB(80, 180,80);
					    }
					    else {
					    	rgb = new RGB(200, 100,100);
					    }
					    frescura.definir_ponto(label.getSize().x +5, county, String.valueOf(cla.numerodeMetodos()),rgb);
					    countx = 0;
					    county +=25;
					    for (int index = 0 ; index<cla.numerodeMetodos(); index++) {
					    	
					    	cla.getMethod(index).getName(); 
					    	
					    	Label label1 = new Label(parent, SWT.NONE);
							 
					    	label1.setText( cla.getMethod(index).getName());
						      label1.setForeground(prata);
						      label1.setFont(new Font(device, fo));
						      int largura = cla.getMethod(index).getName().length()*8;
						      label1.setSize( largura, 20);
						     
						      label1.setLocation(countx+40, county);	
						      lista.add(label1);
						      countx +=largura+40; 
						      MiniCircle frescura1 = new MiniCircle(parent, SWT.NONE);
							    if(cla.getMethod(index).getparametros()<= parametros) {
							    	rgb = new RGB(80, 180,80);
							    }
							    else {
							    	rgb = new RGB(200, 100,100);
							    }
							    frescura1.definir_ponto(countx+5  , county, String.valueOf(cla.getMethod(index).getparametros()),rgb);
					            circles.add(frescura1);
					    }
					    circles.add(frescura);
						DrawLine linha = new DrawLine(parent, SWT.NONE);
						linha.definir_ponto(30, county+20, countx+15);
					    county+= 28;
				}
				
				Button help = new Button(parent, SWT.NONE);
				help.setText("help");
				help.setSize(50,25);
				help.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseUp(MouseEvent arg0) {
						DialogHelper dialog = new DialogHelper(parent.getShell());
					    dialog.create();	
					    dialog.open() ;
					}
					
					@Override
					public void mouseDown(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseDoubleClick(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				});
				help.setLocation(parent1.getParent().getSize().x-50 , 0);
				Button tabela = new Button(parent, SWT.NONE);
				tabela.setText("exibir tabela");
				tabela.setSize(170,25);
				tabela.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseUp(MouseEvent arg0) {
						DialogtableAntipadroes dialog = new DialogtableAntipadroes(parent.getShell());
					    dialog.setEntity(list_of_Entitys);
						
					    dialog.create();	
					    
					    dialog.open() ;
					}
					
					@Override
					public void mouseDown(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseDoubleClick(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				});
				tabela.setLocation(parent1.getParent().getSize().x-220 , 0);
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
					
        		  
        		parent.pack();	
					
        		
        	
        	
        		
				
			
        		
        		
        	 }

			private void  make_path(IResourceDelta[]  recurso) {
				IResourceDelta[]  recurso1   = recurso[0].getAffectedChildren();
				if (recurso1[0].getFullPath().getFileExtension() != null){
				if (recurso1[0].getFullPath().getFileExtension().equals("uml")){
					resource = root.findMember(new Path("/"));
        		    IContainer contain = (IContainer) resource;
					IFile file = contain.getFile(new Path(recurso1[0].getFullPath().toString()));
			        arquivo_uml =  file.getLocationURI();
				   
				}}
				else {
				if (recurso1.length!=0){
					 make_path(recurso1);
			   }
			
				}
				}

			
                 
		
           };
           
        work.addResourceChangeListener(listener);
       parent.setLayout(null);
        //comp.setLayout(rowlayout);
        
        parent.getParent().setBackground(cinza_escuro); 
        
     
	      
	    
	    
	     
			
			
  		
	
		
	}      
        
	    
       
        
	






	

	protected void Decision_color( int valor  , String string) {
		//if (valor>=label_and_text.getvalor_referencia(string)) 
    		//setcolorRangeLine(color1 ,"\t  "+string +": "+ valor);
    	     
    		//else 
    			//setcolorRangeLine(color ,"\t  "+string +": "+ valor);
		
	}
	protected void setcolorRangeLine(Color color2, String line) {
		int lastchar = container.getCharCount();
		container.append(line);
		range = new StyleRange(lastchar, container.getCharCount()-lastchar, color2,null);
		container.setStyleRange(range);
		
	}
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		
	}
}